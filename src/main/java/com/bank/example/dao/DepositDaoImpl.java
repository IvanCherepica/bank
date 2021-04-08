package com.bank.example.dao;

import com.bank.example.model.Deposit;
import org.springframework.stereotype.Repository;

@Repository
public class DepositDaoImpl extends AbstractDao<Long, Deposit> implements DepositDao {
}
