package com.bank.example.dao.operation;

import com.bank.example.dao.AbstractDao;
import com.bank.example.model.operation.AtmWithdraw;
import org.springframework.stereotype.Repository;

@Repository
public class AtmWithdrawDaoImpl extends AbstractDao<Long, AtmWithdraw> implements AtmWithdrawDao {
}
