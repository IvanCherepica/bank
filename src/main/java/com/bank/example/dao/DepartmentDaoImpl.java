package com.bank.example.dao;

import com.bank.example.model.Department;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentDaoImpl extends AbstractDao<Long, Department> implements DepartmentDao {
}
