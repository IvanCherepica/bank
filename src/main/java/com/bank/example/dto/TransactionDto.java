package com.bank.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class TransactionDto {

    private long id;

    private BigDecimal amount;

    private LocalDateTime dateTime;

    private long accountId;

    public TransactionDto(long id, BigDecimal amount, LocalDateTime dateTime, long accountId) {
        this.id = id;
        this.amount = amount;
        this.dateTime = dateTime;
        this.accountId = accountId;
    }
}
