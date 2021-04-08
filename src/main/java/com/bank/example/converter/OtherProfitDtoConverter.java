package com.bank.example.converter;

import com.bank.example.dto.OtherProfitDto;
import com.bank.example.model.operation.OtherProfit;


public class OtherProfitDtoConverter {

    public static OtherProfitDto convertEntityToDto(OtherProfit entity) {
        return OtherProfitDto.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .dateTime(entity.getDateTime())
                .accountId(entity.getAccount().getId())
                .build();
    }
}
