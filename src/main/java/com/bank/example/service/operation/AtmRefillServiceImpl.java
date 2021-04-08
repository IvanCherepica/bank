package com.bank.example.service.operation;

import com.bank.example.dao.operation.AtmRefillDao;
import com.bank.example.model.operation.AtmRefill;
import com.bank.example.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class AtmRefillServiceImpl extends AbstractService<Long, AtmRefill> implements AtmRefillService {

    private final AtmRefillDao atmRefillDao;

    public AtmRefillServiceImpl(AtmRefillDao atmRefillDao) {
        super(atmRefillDao);
        this.atmRefillDao = atmRefillDao;
    }
}
