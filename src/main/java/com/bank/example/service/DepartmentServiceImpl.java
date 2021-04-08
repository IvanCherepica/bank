package com.bank.example.service;

import com.bank.example.dao.DepartmentDao;
import com.bank.example.dao.GenericDao;
import com.bank.example.model.Department;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends AbstractService<Long, Department> implements DepartmentService {

    private final DepartmentDao departmentDao;

    public DepartmentServiceImpl(DepartmentDao departmentDao) {
        super(departmentDao);
        this.departmentDao = departmentDao;
    }
}
