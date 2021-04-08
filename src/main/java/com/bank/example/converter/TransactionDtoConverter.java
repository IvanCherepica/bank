package com.bank.example.converter;

import com.bank.example.dto.OtherProfitDto;
import com.bank.example.dto.TransactionDto;
import com.bank.example.model.operation.OtherProfit;
import com.bank.example.model.operation.Transaction;

public class TransactionDtoConverter {

    public static TransactionDto convertEntityToDto(Transaction entity) {
        return TransactionDto.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .dateTime(entity.getDateTime())
                .accountId(entity.getAccount().getId())
                .build();
    }
}
