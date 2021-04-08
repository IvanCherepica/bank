package com.bank.example.model.orphanRemoval.cascade.persist;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "PersistCascadeDeposit")
@Table(name = "deposit")
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDate openDate;

    private LocalDate closeDate;

    private Float rate;

    private Double sum;

    @ManyToOne(fetch = FetchType.LAZY)
    private com.bank.example.model.orphanRemoval.cascade.persist.Account account;

    public com.bank.example.model.orphanRemoval.cascade.persist.Account getAccount() {
        return account;
    }

    public void setAccount(com.bank.example.model.orphanRemoval.cascade.persist.Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deposit deposit = (Deposit) o;
        return Objects.equals(id, deposit.id) &&
                Objects.equals(openDate, deposit.openDate) &&
                Objects.equals(closeDate, deposit.closeDate) &&
                Objects.equals(rate, deposit.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, openDate, closeDate, rate);
    }
}
