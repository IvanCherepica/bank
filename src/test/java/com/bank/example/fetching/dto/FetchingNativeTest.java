package com.bank.example.fetching.dto;

import com.bank.example.BaseTest;
import com.bank.example.config.BankApplication;
import com.bank.example.dto.ManagerDto;
import com.bank.example.model.Manager;
import org.hibernate.SQLQuery;
import org.hibernate.annotations.QueryHints;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.ResultTransformer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.List;

@SpringBootTest(classes = BankApplication.class)
public class FetchingNativeTest extends BaseTest {

    private final static int QUERY_AMOUNT = 5000;

    @Test
    void testEntity() {

        long queryExecutionTime = 0;

        for (int i = 0; i < QUERY_AMOUNT; i++) {

            long start = System.nanoTime();
            em.createNativeQuery(
                    "SELECT m.id, m.phone, m.passport_number, m.department_id FROM manager m",
                    Manager.class
            )
                    .getResultList();
            long end = System.nanoTime();
            queryExecutionTime += end - start;
        }

        System.out.println("Суммарное время выполнения " + QUERY_AMOUNT + " запросов: " + queryExecutionTime);
    }

    @Test
    void testImmutableEntity() {

        long queryExecutionTime = 0;

        for (int i = 0; i < QUERY_AMOUNT; i++) {

            long start = System.nanoTime();
            em.createNativeQuery(
                    "SELECT m.id, m.phone, m.passport_number, m.department_id FROM manager m",
                    com.bank.example.model.fetching.Manager.class
            )
                    .getResultList();
            long end = System.nanoTime();
            queryExecutionTime += end - start;
        }

        System.out.println("Суммарное время выполнения " + QUERY_AMOUNT + " запросов: " + queryExecutionTime);
    }

    @Test
    void testReadOnlyEntity() {

        long queryExecutionTime = 0;

        for (int i = 0; i < QUERY_AMOUNT; i++) {

            long start = System.nanoTime();
            em.createNativeQuery(
                    "SELECT m.id, m.phone, m.passport_number, m.department_id FROM manager m",
                    Manager.class
            )
                    .setHint(QueryHints.READ_ONLY, true)
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
            List<Tuple> tupleList = em.createNativeQuery(
                    "SELECT m.id, m.phone, m.passport_number FROM manager m",
                    Tuple.class
            )
                    .getResultList();

            for (Tuple tuple : tupleList) {
                ManagerDto.builder()
                        .id(tuple.get(0, BigInteger.class).longValue())
                        .phone(tuple.get(1, String.class))
                        .passportNumber(tuple.get(2, String.class))
                        .build();
            }

            long end = System.nanoTime();
            queryExecutionTime += end - start;
        }

        System.out.println("Суммарное время выполнения " + QUERY_AMOUNT + " запросов: " + queryExecutionTime);
    }

    @Test
    void testResultTransformer() {

        long queryExecutionTime = 0;

        for (int i = 0; i < QUERY_AMOUNT; i++) {

            long start = System.nanoTime();
            em.createNativeQuery("SELECT m.id, m.phone, m.passport_number FROM manager m")
                    .unwrap(SQLQuery.class)
                    .setResultTransformer(new ResultTransformer() {

                        @Override
                        public Object transformTuple(Object[] objects, String[] strings) {
                            return ManagerDto.builder()
                                    .id(((BigInteger) objects[0]).longValue())
                                    .phone((String) objects[1])
                                    .passportNumber((String )objects[2])
                                    .build();
                        }

                        @Override
                        public List transformList(List list) {
                            return list;
                        }
                    })
                    .getResultList();
            long end = System.nanoTime();
            queryExecutionTime += end - start;
        }

        System.out.println("Суммарное время выполнения " + QUERY_AMOUNT + " запросов: " + queryExecutionTime);
    }

    @Test
    void testAliasToBeanResultTransformer() {

        long queryExecutionTime = 0;

        for (int i = 0; i < QUERY_AMOUNT; i++) {

            long start = System.nanoTime();
            em.createNativeQuery("SELECT m.id, m.phone, m.passport_number AS passportNumber FROM manager m")
                    .unwrap(SQLQuery.class)
                    .setResultTransformer(new AliasToBeanResultTransformer(ManagerDto.class))
                    .getResultList();
            long end = System.nanoTime();
            queryExecutionTime += end - start;
        }

        System.out.println("Суммарное время выполнения " + QUERY_AMOUNT + " запросов: " + queryExecutionTime);
    }
}
