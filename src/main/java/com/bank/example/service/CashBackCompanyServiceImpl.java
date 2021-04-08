package com.bank.example.service;

import com.bank.example.dao.CashBackCompanyDao;
import com.bank.example.dao.GenericDao;
import com.bank.example.model.CashBackCompany;
import org.springframework.stereotype.Service;

@Service
public class CashBackCompanyServiceImpl extends AbstractService<Long, CashBackCompany> implements CashBackCompanyService {

    private final CashBackCompanyDao cashBackCompanyDao;

    public CashBackCompanyServiceImpl(CashBackCompanyDao cashBackCompanyDao) {
        super(cashBackCompanyDao);
        this.cashBackCompanyDao = cashBackCompanyDao;
    }
}
