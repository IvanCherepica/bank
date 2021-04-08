package com.bank.example.model.operation;

import com.bank.example.model.Account;
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
public class AtmTransaction extends Transaction {

    @ManyToOne(fetch = FetchType.LAZY)
    private Atm atm;

    public AtmTransaction(LocalDateTime dateTime, Account account, Atm atm) {
        super(dateTime, account);
        this.atm = atm;
    }

    public AtmTransaction(BigDecimal amount, LocalDateTime dateTime, Account account, Atm atm) {
        super(amount, dateTime, account);
        this.atm = atm;
    }
}
