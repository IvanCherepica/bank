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
public class OtherProfit extends Transaction {

    public OtherProfit(LocalDateTime dateTime, Account account) {
        super(dateTime, account);
    }

    public OtherProfit(BigDecimal amount, LocalDateTime dateTime, Account account) {
        super(amount, dateTime, account);
    }
}
