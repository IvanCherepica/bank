package com.bank.example.dao.operation;

import com.bank.example.dao.AbstractDao;
import com.bank.example.model.operation.CashBack;
import org.springframework.stereotype.Repository;

@Repository
public class CashBackDaoImpl extends AbstractDao<Long, CashBack> implements CashBackDao {
}
