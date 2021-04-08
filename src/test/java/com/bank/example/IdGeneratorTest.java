package com.bank.example;

import com.bank.example.config.BankApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

@SpringBootTest(classes = BankApplication.class)
public class IdGeneratorTest extends BaseTest {

    @Test
    @Commit
    void identityTest() {
        for (int i = 0; i < 20; i++) {
            em.persist(new com.bank.example.model.identity.Department());

            if (i % 10 == 0) {
                em.flush();
            }
        }
    }

    @Test
    @Commit
    void sequenceTest() {
        for (int i = 0; i < 20; i++) {
            em.persist(new com.bank.example.model.sequence.Department());

            if (i % 10 == 0) {
                em.flush();
            }
        }
    }

    @Test
    @Commit
    void tableTest() {
        for (int i = 0; i < 20; i++) {
            em.persist(new com.bank.example.model.table.Department());

            if (i % 10 == 0) {
                em.flush();
            }
        }
    }
}
