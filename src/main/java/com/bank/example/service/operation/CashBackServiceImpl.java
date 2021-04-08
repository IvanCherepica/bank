package com.bank.example.service.operation;

import com.bank.example.dao.GenericDao;
import com.bank.example.dao.operation.CashBackDao;
import com.bank.example.model.operation.CashBack;
import com.bank.example.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class CashBackServiceImpl extends AbstractService<Long, CashBack> implements CashBackService {

    private final CashBackDao cashBackDao;

    public CashBackServiceImpl(CashBackDao cashBackDao) {
        super(cashBackDao);
        this.cashBackDao = cashBackDao;
    }
}
