package com.bank.example.converter;

import com.bank.example.dto.AtmTransactionDto;
import com.bank.example.model.operation.AtmTransaction;

public class AtmTransactionDtoConverter {

    public static AtmTransactionDto convertEntityToDto(AtmTransaction entity) {
        return AtmTransactionDto.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .dateTime(entity.getDateTime())
                .accountId(entity.getAccount().getId())
                .build();
    }
}
