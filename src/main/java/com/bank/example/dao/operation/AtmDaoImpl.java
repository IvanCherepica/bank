package com.bank.example.dao.operation;

import com.bank.example.dao.AbstractDao;
import com.bank.example.model.operation.Atm;
import org.springframework.stereotype.Repository;

@Repository
public class AtmDaoImpl extends AbstractDao<Long, Atm> implements AtmDao {
}
