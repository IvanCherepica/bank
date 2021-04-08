package com.bank.example.dao;

import com.bank.example.model.Manager;
import org.springframework.stereotype.Repository;

@Repository
public class ManagerDaoImpl extends AbstractDao<Long, Manager> implements ManagerDao {
}
