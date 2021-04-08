package com.bank.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AtmTransactionDto {

    private long id;

    private BigDecimal amount;

    private LocalDateTime dateTime;

    private long accountId;

    private long atmId;

    public AtmTransactionDto(long id, BigDecimal amount, LocalDateTime dateTime, long accountId, long atmId) {
        this.id = id;
        this.amount = amount;
        this.dateTime = dateTime;
        this.accountId = accountId;
        this.atmId = atmId;
    }
}
