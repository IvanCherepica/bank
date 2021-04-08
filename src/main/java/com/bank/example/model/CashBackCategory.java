package com.bank.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CashBackCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Manager uploader;

    @ManyToOne(fetch = FetchType.LAZY)
    private Manager editor;

    @ManyToMany
    private List<Account> accounts = new ArrayList<>();

    @ManyToMany
    private List<CashBackCompany> cashBackCompanies = new ArrayList<>();

    public void addAccount(Account account) {
        accounts.add(account);
        account.getCashBackCategories().add(this);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
        account.getCashBackCategories().remove(this);
    }

    public void addCashBackCompany(CashBackCompany cashBackCompany) {
        cashBackCompanies.add(cashBackCompany);
        cashBackCompany.getCashBackCategories().add(this);
    }

    public void removeCashBackCompany(CashBackCompany cashBackCompany) {
        cashBackCompanies.remove(cashBackCompany);
        cashBackCompany.getCashBackCategories().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CashBackCategory that = (CashBackCategory) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
