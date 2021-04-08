package com.bank.example.service.operation;

import com.bank.example.dao.GenericDao;
import com.bank.example.dao.operation.InterestsDao;
import com.bank.example.model.operation.Interests;
import com.bank.example.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class InterestsServiceImpl extends AbstractService<Long, Interests> implements InterestsService {

    private final InterestsDao interestsDao;

    public InterestsServiceImpl(InterestsDao interestsDao) {
        super(interestsDao);
        this.interestsDao = interestsDao;
    }
}
