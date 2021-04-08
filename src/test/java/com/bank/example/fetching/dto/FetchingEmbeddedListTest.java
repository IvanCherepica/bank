package com.bank.example.fetching.dto;

import com.bank.example.BaseTest;
import com.bank.example.config.BankApplication;
import com.bank.example.dto.DepartmentManagerDto;
import com.bank.example.dto.ManagerDto;
import com.bank.example.model.Department;
import org.hibernate.Query;
import org.hibernate.transform.ResultTransformer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = BankApplication.class)
public class FetchingEmbeddedListTest extends BaseTest {

    private final static int QUERY_AMOUNT = 5000;

    @Test
    void testEntity() {

        long queryExecutionTime = 0;

        for (int i = 0; i < QUERY_AMOUNT; i++) {

            long start = System.nanoTime();
            List<Department> departmentList = em.createQuery(
                    "SELECT d FROM Department d JOIN FETCH d.managers",
                    Department.class
            )
                    .getResultList();
            long end = System.nanoTime();
            queryExecutionTime += end - start;
        }

        System.out.println("Суммарное время выполнения " + QUERY_AMOUNT + " запросов: " + queryExecutionTime);
    }

    @Test
    void testTuple() {

        long queryExecutionTime = 0;

        for (int i = 0; i < QUERY_AMOUNT; i++) {

            long start = System.nanoTime();
            List<Tuple> tupleList = em.createQuery(
                    "SELECT " +
                            "d.id AS departmentId, d.name AS departmentName, " +
                            "m.id AS managerId, m.phone AS phone, " +
                            "m.passportNumber AS passportNumber " +
                            "FROM Department d JOIN d.managers m",
                    Tuple.class)
                    .getResultList();

            List<DepartmentManagerDto> departmentManagerDtoListList = DepartmentManagerDtoTupleTransformer.transform(tupleList);

            long end = System.nanoTime();
            queryExecutionTime += end - start;
        }

        System.out.println("Суммарное время выполнения " + QUERY_AMOUNT + " запросов: " + queryExecutionTime);
    }

    private static class DepartmentManagerDtoTupleTransformer {

        static List<DepartmentManagerDto> transform(List<Tuple> tupleList) {

            Map<Long, List<ManagerDto>> departmentIdManagersMap = new HashMap<>(tupleList.size());
            List<DepartmentManagerDto> result = new ArrayList<>();

            for (Tuple tuple : tupleList) {
                Long departmentId = tuple.get("departmentId", Long.class);
                ManagerDto managerDto = createManagerDto(tuple);

                if (!departmentIdManagersMap.containsKey(departmentId)) {
                    DepartmentManagerDto departmentManagerDto = createDepartmentManagerDto(tuple);

                    departmentIdManagersMap.put(departmentId, new ArrayList<>());
                    result.add(departmentManagerDto);
                }

                departmentIdManagersMap.get(departmentId).add(managerDto);
            }

            for (DepartmentManagerDto dto : result) {
                dto.setManagerDtoList(departmentIdManagersMap.get(dto.getId()));
            }

            return result;
        }

        private static ManagerDto createManagerDto(Tuple tuple) {
            return ManagerDto.builder()
                    .id(tuple.get("managerId", Long.class))
                    .phone(tuple.get("phone", String.class))
                    .passportNumber(tuple.get("passportNumber", String.class))
                    .build();
        }

        private static DepartmentManagerDto createDepartmentManagerDto(Tuple tuple) {
            return DepartmentManagerDto.builder()
                    .id(tuple.get("departmentId", Long.class))
                    .name(tuple.get("departmentName", String.class))
                    .build();
        }
    }

    @Test
    void testResultTransformer() {

        long queryExecutionTime = 0;

        for (int i = 0; i < QUERY_AMOUNT; i++) {

            long start = System.nanoTime();
            List<DepartmentManagerDto> departmentManagerDtoListList = em.createQuery("SELECT " +
                    "d.id AS departmentId, d.name AS departmentName, " +
                    "m.id AS managerId, m.phone AS phone, " +
                    "m.passportNumber AS passportNumber " +
                    "FROM Department d JOIN d.managers m")
                    .unwrap(Query.class)
                    .setResultTransformer(new DepartmentManagerDtoResultTransformer())
                    .getResultList();
            long end = System.nanoTime();
            queryExecutionTime += end - start;
        }

        System.out.println("Суммарное время выполнения " + QUERY_AMOUNT + " запросов: " + queryExecutionTime);
    }

    private static class DepartmentManagerDtoResultTransformer implements ResultTransformer {

        private final Map<Long, List<ManagerDto>> departmentIdManagersMap = new HashMap<>();

        @Override
        public Object transformTuple(Object[] objects, String[] strings) {

            Long departmentId = (Long) objects[0];
            ManagerDto managerDto = createManagerDto(objects);
            DepartmentManagerDto departmentManagerDto = createDepartmentManagerDto(objects);

            if (!departmentIdManagersMap.containsKey(departmentId)) {

                List<ManagerDto> managerDtoList = new ArrayList<>();
                managerDtoList.add(managerDto);

                departmentIdManagersMap.put(departmentId, managerDtoList);
            }

            departmentIdManagersMap.get(departmentId).add(managerDto);

            return departmentManagerDto;
        }

        @Override
        public List<DepartmentManagerDto> transformList(List list) {
            for (Object object : list) {
                DepartmentManagerDto dto = (DepartmentManagerDto) object;
                dto.setManagerDtoList(departmentIdManagersMap.get(dto.getId()));
            }
            return list;
        }

        private ManagerDto createManagerDto(Object[] objects) {
            return ManagerDto.builder()
                    .id((long) objects[2])
                    .phone((String) objects[3])
                    .passportNumber((String) objects[4])
                    .build();
        }

        private DepartmentManagerDto createDepartmentManagerDto(Object[] objects) {
            return DepartmentManagerDto.builder()
                    .id((long) objects[0])
                    .name((String) objects[1])
                    .build();
        }
    }
}
