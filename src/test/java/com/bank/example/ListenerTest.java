package com.bank.example;

import com.bank.example.config.BankApplication;
import com.bank.example.model.Department;
import com.bank.example.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

@SpringBootTest(classes = BankApplication.class)
public class ListenerTest extends BaseTest {

    @Autowired
    private DepartmentService departmentService;

    @Test
    @Commit
    void persistTest() {
        Department department = new Department("Отдел обратной связи");
        departmentService.persist(department);
    }

    @Test
    @Commit
    void updateTest() {
        Department department = saveDepartment();
        department.setName("Отдел обратной связи и поддежки");
    }

    @Test
    @Commit
    void removeTest() {
        Department department = saveDepartment();
        departmentService.remove(department);
    }

    private Department saveDepartment() {
        Department department = new Department("Отдел обратной связи");
        em.persist(department);
        em.flush();
        return department;
    }
}