package com.bank.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CashBackDto extends OtherProfitDto {

    private long cashBackCompanyId;

    public CashBackDto(long id, BigDecimal amount, LocalDateTime dateTime, long accountId) {
        super(id, amount, dateTime, accountId);
    }
}
