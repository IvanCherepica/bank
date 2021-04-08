package com.bank.example.fetching.dto;

import com.bank.example.BaseTest;
import com.bank.example.config.BankApplication;
import com.bank.example.dto.ManagerDto;
import org.hibernate.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.ResultTransformer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.List;

@SpringBootTest(classes = BankApplication.class)
public class FetchingAllFieldsTest extends BaseTest {

    private final static int QUERY_AMOUNT = 5000;

    @Test
    void testTupleHQLAllFields() {

        long queryExecutionTime = 0;

        for (int i = 0; i < QUERY_AMOUNT; i++) {

            long start = System.nanoTime();
            em.createQuery("SELECT m FROM Manager m",
                    Tuple.class)
                    .getResultList();
            long end = System.nanoTime();
            queryExecutionTime += end - start;
        }

        System.out.println("Суммарное время выполнения " + QUERY_AMOUNT + " запросов: " + queryExecutionTime);
    }

    @Test
    void testTupleNativeAllFields() {

        long queryExecutionTime = 0;

        for (int i = 0; i < QUERY_AMOUNT; i++) {

            long start = System.nanoTime();
            em.createNativeQuery("SELECT * FROM manager m",
                    Tuple.class)
                    .getResultList();
            long end = System.nanoTime();
            queryExecutionTime += end - start;
        }

        System.out.println("Суммарное время выполнения " + QUERY_AMOUNT + " запросов: " + queryExecutionTime);
    }

    @Test
    void testResultTransformerAllFields() {

        long queryExecutionTime = 0;

        for (int i = 0; i < QUERY_AMOUNT; i++) {

            long start = System.nanoTime();
            em.createNativeQuery("SELECT * FROM manager m")
                    .unwrap(Query.class)
                    .setResultTransformer(new ResultTransformer() {

                        @Override
                        public Object transformTuple(Object[] objects, String[] strings) {
                            return ManagerDto.builder()
                                    .id(((BigInteger) objects[0]).longValue())
                                    .passportNumber((String )objects[1])
                                    .phone((String) objects[2])
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
    void testAliasToBeanResultTransformerAllFields() {

        long queryExecutionTime = 0;

        for (int i = 0; i < QUERY_AMOUNT; i++) {

            long start = System.nanoTime();
            em.createQuery("SELECT m FROM Manager m")
                    .unwrap(Query.class)
                    .setResultTransformer(new AliasToBeanResultTransformer(ManagerDto.class))
                    .getResultList();
            long end = System.nanoTime();
            queryExecutionTime += end - start;
        }

        System.out.println("Суммарное время выполнения " + QUERY_AMOUNT + " запросов: " + queryExecutionTime);
    }
}
