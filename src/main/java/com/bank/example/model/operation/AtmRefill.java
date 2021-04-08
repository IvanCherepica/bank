package com.bank.example.model.operation;

import com.bank.example.model.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class AtmRefill extends AtmTransaction {

    public AtmRefill(LocalDateTime dateTime, Account account, Atm atm) {
        super(dateTime, account, atm);
    }

    public AtmRefill(BigDecimal amount, LocalDateTime dateTime, Account account, Atm atm) {
        super(amount, dateTime, account, atm);
    }
}
