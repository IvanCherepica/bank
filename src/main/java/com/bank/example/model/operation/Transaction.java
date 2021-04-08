package com.bank.example.model.operation;

import com.bank.example.model.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    public Transaction(LocalDateTime dateTime, Account account) {
        this.dateTime = dateTime;
        this.account = account;
    }

    public Transaction(BigDecimal amount, LocalDateTime dateTime, Account account) {
        this.amount = amount;
        this.dateTime = dateTime;
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction operation = (Transaction) o;
        return Objects.equals(id, operation.id) &&
                Objects.equals(dateTime, operation.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime);
    }
}
