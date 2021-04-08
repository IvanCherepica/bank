package com.bank.example.service;

import com.bank.example.dao.GenericDao;
import com.bank.example.dao.OperatorRatingDao;
import com.bank.example.model.OperatorRating;
import org.springframework.stereotype.Service;

@Service
public class OperatorRatingServiceImpl extends AbstractService<Long, OperatorRating> implements OperatorRatingService {

    private final OperatorRatingDao operatorRatingDao;

    public OperatorRatingServiceImpl(OperatorRatingDao operatorRatingDao) {
        super(operatorRatingDao);
        this.operatorRatingDao = operatorRatingDao;
    }
}
