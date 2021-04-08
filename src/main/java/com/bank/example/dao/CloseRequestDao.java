package com.bank.example.dao;

import com.bank.example.model.CloseRequest;

public interface CloseRequestDao extends GenericDao<Long, CloseRequest> {

    CloseRequest getCloseRequestByAccountId(Long accountId);
}
