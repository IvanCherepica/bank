package com.bank.example.model.operation;

import com.bank.example.model.Account;
import com.bank.example.model.Deposit;
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
public class Interests extends OtherProfit {

    @ManyToOne(fetch = FetchType.LAZY)
    private Deposit deposit;

    public Interests(LocalDateTime dateTime, Account account, Deposit deposit) {
        super(dateTime, account);
        this.deposit = deposit;
    }

    public Interests(BigDecimal amount, LocalDateTime dateTime, Account account, Deposit deposit) {
        super(amount, dateTime, account);
        this.deposit = deposit;
    }
}
