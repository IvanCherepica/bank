package com.bank.example.util.dto.transaction;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class TransactionUtilDto {

    private long id;

    private TransactionUtilType transactionType;

    private BigDecimal amount;

    private LocalDateTime dateTime;

    private long accountId;
}
