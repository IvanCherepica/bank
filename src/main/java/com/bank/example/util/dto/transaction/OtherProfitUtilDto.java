package com.bank.example.util.dto.transaction;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class OtherProfitUtilDto {

    private long id;

    private TransactionUtilType otherProfitType;

    private BigDecimal amount;

    private LocalDateTime dateTime;

    private long accountId;
}
