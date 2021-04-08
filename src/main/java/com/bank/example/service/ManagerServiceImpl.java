package com.bank.example.service;

import com.bank.example.dao.GenericDao;
import com.bank.example.dao.ManagerDao;
import com.bank.example.model.Manager;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImpl extends AbstractService<Long, Manager> implements ManagerService {

    private final ManagerDao managerDao;

    public ManagerServiceImpl(ManagerDao managerDao) {
        super(managerDao);
        this.managerDao = managerDao;
    }
}
