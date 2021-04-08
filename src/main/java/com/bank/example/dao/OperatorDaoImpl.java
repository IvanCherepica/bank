package com.bank.example.dao;

import com.bank.example.model.Operator;
import org.springframework.stereotype.Repository;

@Repository
public class OperatorDaoImpl extends AbstractDao<Long, Operator> implements OperatorDao {
}
