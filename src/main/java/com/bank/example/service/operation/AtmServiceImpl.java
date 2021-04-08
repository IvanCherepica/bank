package com.bank.example.service.operation;

import com.bank.example.dao.GenericDao;
import com.bank.example.dao.operation.AtmDao;
import com.bank.example.model.operation.Atm;
import com.bank.example.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class AtmServiceImpl extends AbstractService<Long, Atm> implements AtmService {

    private final AtmDao atmDao;

    public AtmServiceImpl(AtmDao atmDao) {
        super(atmDao);
        this.atmDao = atmDao;
    }
}
