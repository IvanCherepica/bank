package com.bank.example.dao.operation;

import com.bank.example.dao.GenericDao;
import com.bank.example.dto.AtmTransactionDto;
import com.bank.example.model.operation.AtmTransaction;

import java.util.List;

public interface AtmTransactionDao extends GenericDao<Long, AtmTransaction> {

    List<AtmTransactionDto> getAtmTransactionDtosByAccountId(Long accountId);
}
