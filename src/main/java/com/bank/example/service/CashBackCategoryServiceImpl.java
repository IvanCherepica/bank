package com.bank.example.service;

import com.bank.example.dao.CashBackCategoryDao;
import com.bank.example.dao.GenericDao;
import com.bank.example.model.Account;
import com.bank.example.model.CashBackCategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CashBackCategoryServiceImpl extends AbstractService<Long, CashBackCategory> implements CashBackCategoryService {

    private final CashBackCategoryDao cashBackCategoryDao;

    public CashBackCategoryServiceImpl(CashBackCategoryDao cashBackCategoryDao) {
        super(cashBackCategoryDao);
        this.cashBackCategoryDao = cashBackCategoryDao;
    }

}
