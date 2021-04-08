package com.bank.example.dao.operation;

import com.bank.example.dao.AbstractDao;
import com.bank.example.model.operation.Interests;
import org.springframework.stereotype.Repository;

@Repository
public class InterestsDaoImpl extends AbstractDao<Long, Interests> implements InterestsDao {
}
