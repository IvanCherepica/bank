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
public class InterestsUtilDto {

    private Long id;

    private BigDecimal amount;

    private long accountId;

    private long depositId;

    public InterestsUtilDto(BigDecimal amount, long accountId, long depositId) {
        this.amount = amount;
        this.accountId = accountId;
        this.depositId = depositId;
    }
}
