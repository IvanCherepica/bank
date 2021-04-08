package com.bank.example.service.operation;

import com.bank.example.dto.AtmTransactionDto;
import com.bank.example.model.operation.AtmTransaction;
import com.bank.example.service.GenericService;

import java.util.List;

public interface AtmTransactionService extends GenericService<Long, AtmTransaction> {

    List<AtmTransactionDto> getAtmTransactionDtosByAccountId(Long accountId);
}
