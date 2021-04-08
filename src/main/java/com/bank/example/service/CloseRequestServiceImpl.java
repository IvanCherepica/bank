package com.bank.example.service;

import com.bank.example.dao.CloseRequestDao;
import com.bank.example.dao.GenericDao;
import com.bank.example.model.CloseRequest;
import org.springframework.stereotype.Service;

@Service
public class CloseRequestServiceImpl extends AbstractService<Long, CloseRequest> implements CloseRequestService {

    private final CloseRequestDao closeRequestDao;

    public CloseRequestServiceImpl(CloseRequestDao closeRequestDao) {
        super(closeRequestDao);
        this.closeRequestDao = closeRequestDao;
    }

    @Override
    public CloseRequest getCloseRequestByAccountId(Long accountId) {
        return closeRequestDao.getCloseRequestByAccountId(accountId);
    }
}
