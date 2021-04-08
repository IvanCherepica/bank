package com.bank.example.model.operation;

import com.bank.example.model.Account;
import com.bank.example.model.CashBackCompany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CashBack extends OtherProfit {

    @ManyToOne(fetch = FetchType.LAZY)
    private CashBackCompany cashBackCompany;

    public CashBack(LocalDateTime dateTime, Account account, CashBackCompany cashBackCompany) {
        super(dateTime, account);
        this.cashBackCompany = cashBackCompany;
    }

    public CashBack(BigDecimal amount, LocalDateTime dateTime, Account account, CashBackCompany cashBackCompany) {
        super(amount, dateTime, account);
        this.cashBackCompany = cashBackCompany;
    }
}
