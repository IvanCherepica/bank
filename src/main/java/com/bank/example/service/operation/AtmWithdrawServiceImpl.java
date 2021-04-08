package com.bank.example.service.operation;

import com.bank.example.dao.operation.AtmWithdrawDao;
import com.bank.example.model.operation.AtmWithdraw;
import com.bank.example.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class AtmWithdrawServiceImpl extends AbstractService<Long, AtmWithdraw> implements AtmWithdrawService {

    private final AtmWithdrawDao atmWithdrawDao;

    public AtmWithdrawServiceImpl(AtmWithdrawDao atmWithdrawDao) {
        super(atmWithdrawDao);
        this.atmWithdrawDao = atmWithdrawDao;
    }
}
