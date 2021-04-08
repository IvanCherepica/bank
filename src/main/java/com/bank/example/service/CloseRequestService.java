package com.bank.example.service;

import com.bank.example.model.CloseRequest;

public interface CloseRequestService extends GenericService<Long, CloseRequest> {

    CloseRequest getCloseRequestByAccountId(Long accountId);
}
