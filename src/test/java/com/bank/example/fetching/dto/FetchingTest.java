package com.bank.example.fetching.dto;

import com.bank.example.BaseTest;
import com.bank.example.config.BankApplication;
import com.bank.example.dto.ManagerDto;
import com.bank.example.model.Manager;
import com.bank.example.service.TestDataInitServiceImpl;
import org.hibernate.Query;
import org.hibernate.annotations.QueryHints;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.ResultTransformer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.List;


@SpringBootTest(classes = BankApplication.class)
public class FetchingTest extends BaseTest {

    private final static int QUERY_AMOUNT = 5000;

    @Test
    void testEntity() {

        long queryExecutionTime = 0;

        for (int i = 0; i < QUERY_AMOUNT; i++) {

            long start = System.nanoTime();
            em.createQuery("SELECT m FROM Manager m", Manager.class).getResultList();
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
            em.createQuery("SELECT m FROM com.bank.example.model.fetching.Manager m",
                    com.bank.example.model.fetching.Manager.class)
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
            em.createQuery("SELECT m FROM Manager m", Manager.class)
                    .setHint(QueryHints.READ_ONLY, true)
                    .getResultList();
            long end = System.nanoTime();
            queryExecutionTime += end - start;
        }

        System.out.println("Суммарное время выполнения " + QUERY_AMOUNT + " запросов: " + queryExecutionTime);
    }

    @Test
    void testConstructorExpression() {

        long queryExecutionTime = 0;

        for (int i = 0; i < QUERY_AMOUNT; i++) {

            long start = System.nanoTime();
            em.createQuery("SELECT new com.bank.example.dto.ManagerDto(m.id, m.phone, m.passportNumber) FROM Manager m",
                    com.bank.example.dto.ManagerDto.class)
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
            List<Tuple> tupleList = em.createQuery("SELECT m.id, m.phone, m.passportNumber FROM Manager m",
                    Tuple.class)
                    .getResultList();

            for (Tuple tuple : tupleList) {
                ManagerDto.builder()
                        .id(tuple.get(0, Long.class))
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
            em.createQuery("SELECT m.id, m.phone, m.passportNumber FROM Manager m")
                    .unwrap(Query.class)
                    .setResultTransformer(new ResultTransformer() {

                        @Override
                        public Object transformTuple(Object[] objects, String[] strings) {
                            return ManagerDto.builder()
                                    .id((long) objects[0])
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
            em.createQuery("SELECT m.id, m.phone, m.passportNumber FROM Manager m")
                    .unwrap(Query.class)
                    .setResultTransformer(new AliasToBeanResultTransformer(ManagerDto.class))
                    .getResultList();
            long end = System.nanoTime();
            queryExecutionTime += end - start;
        }

        System.out.println("Суммарное время выполнения " + QUERY_AMOUNT + " запросов: " + queryExecutionTime);
    }
}
