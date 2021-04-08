package com.bank.example.service;

import com.bank.example.dao.GenericDao;
import com.bank.example.dao.TariffDao;
import com.bank.example.model.Tariff;
import org.springframework.stereotype.Service;

@Service
public class TariffServiceImpl extends AbstractService<Long, Tariff> implements TariffService {


    public TariffServiceImpl(TariffDao tariffDao) {
        super(tariffDao);
    }
}
