package com.bank.example;

import com.bank.example.config.BankApplication;
import com.bank.example.model.identity.Department;
import com.bank.example.sqltracker.AssertSqlCount;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = BankApplication.class)
public class BatchingTest extends BaseTest {

    @Test
    @Commit
    void persistTest() {
        AssertSqlCount.reset();

        for (int i = 0; i < 10; i++) {
            em.persist(new com.bank.example.model.identity.Department());
        }
        em.flush();

        AssertSqlCount.assertInsertCount(10);
    }

    @Test
    @Commit
    void updateTest() {
        AssertSqlCount.reset();

        List<com.bank.example.model.identity.Department> departmentList = saveDepartments();

        for (com.bank.example.model.identity.Department department : departmentList) {
            department.setName("Новый отдел");
        }
        em.flush();

        AssertSqlCount.assertUpdateCount(1);
    }

    @Test
    @Commit
    void removeTest() {
        AssertSqlCount.reset();

        List<com.bank.example.model.identity.Department> departmentList = saveDepartments();

        for (com.bank.example.model.identity.Department department : departmentList) {
            em.remove(department);
        }
        em.flush();

        AssertSqlCount.assertDeleteCount(1);
    }

    private List<Department> saveDepartments() {
        List<com.bank.example.model.identity.Department> departmentList = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Department department = new com.bank.example.model.identity.Department();
            em.persist(department);
            departmentList.add(department);
        }
        em.flush();
        return departmentList;
    }
}
