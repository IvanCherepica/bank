package com.bank.example.model.orphanRemoval.cascade.persist;

import com.bank.example.model.CashBackCategory;
import com.bank.example.model.CashBackCompany;
import com.bank.example.model.Deposit;
import com.bank.example.model.DocumentScans;
import com.bank.example.model.Settings;
import com.bank.example.model.Tariff;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity(name = "PersistCascadeAccount")
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firstName;

    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tariff tariff;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Settings settings;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DocumentScans documentScans;

    @OneToMany(mappedBy = "account", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<com.bank.example.model.orphanRemoval.cascade.persist.Deposit> deposits = new ArrayList<>();

    public Account() {
    }

    public Account(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Account(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public List<com.bank.example.model.orphanRemoval.cascade.persist.Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<com.bank.example.model.orphanRemoval.cascade.persist.Deposit> deposits) {
        this.deposits = deposits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(firstName, account.firstName) &&
                Objects.equals(lastName, account.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
}