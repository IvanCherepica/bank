package com.bank.example.dao.operation;

import com.bank.example.dao.AbstractDao;
import com.bank.example.model.operation.AtmRefill;
import org.springframework.stereotype.Repository;

@Repository
public class AtmRefillDaoImpl extends AbstractDao<Long, AtmRefill> implements AtmRefillDao {
}
