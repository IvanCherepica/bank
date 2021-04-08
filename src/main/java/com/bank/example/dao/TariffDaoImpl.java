package com.bank.example.dao;

import com.bank.example.model.Tariff;
import org.springframework.stereotype.Repository;

@Repository
public class TariffDaoImpl extends AbstractDao<Long, Tariff> implements TariffDao {
}
