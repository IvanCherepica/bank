package com.bank.example.dao;

import com.bank.example.model.Employee;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDaoImpl extends AbstractDao<Long, Employee> implements EmployeeDao {
}
