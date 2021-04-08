package com.bank.example.util.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AtmTransactionUtilDto {

    private long id;

    private TransactionUtilType atmTransactionType;

    private BigDecimal amount;

    private LocalDateTime dateTime;

    private long accountId;

    private long atmId;

    public AtmTransactionUtilDto(BigDecimal amount, long accountId, long atmId) {
        this.amount = amount;
        this.accountId = accountId;
        this.atmId = atmId;
    }
}