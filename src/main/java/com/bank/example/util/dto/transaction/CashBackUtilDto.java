package com.bank.example.util.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashBackUtilDto {

    private Long id;

    private BigDecimal amount;

    private long accountId;

    private long cashBackCompanyId;

    public CashBackUtilDto(BigDecimal amount, long accountId, long cashBackCompanyId) {
        this.amount = amount;
        this.accountId = accountId;
        this.cashBackCompanyId = cashBackCompanyId;
    }
}
