package com.bank.example;

import com.bank.example.config.BankApplication;
import com.bank.example.model.Employee;
import com.bank.example.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

@SpringBootTest(classes = BankApplication.class)
public class PolymorphicQueryTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    @Commit
    void selectEmployee() {
        employeeService.getAll();
    }

    @Test
    @Commit
    void updateEmployee() {
        Employee employee = employeeService.getByKey(1L);
        employeeService.update(employee);
    }
}
