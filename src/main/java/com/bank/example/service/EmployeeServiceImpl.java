package com.bank.example.service;

import com.bank.example.dao.EmployeeDao;
import com.bank.example.dao.GenericDao;
import com.bank.example.model.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends AbstractService<Long, Employee> implements EmployeeService {

    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        super(employeeDao);
    }
}
