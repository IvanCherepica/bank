package com.bank.example.model.cascade;

import com.bank.example.model.Account;
import com.bank.example.model.Manager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "CascadeCBB")
@Table(name = "cash_back_category", schema = "public")
public class CashBackCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Manager uploader;

    @ManyToOne(fetch = FetchType.LAZY)
    private Manager editor;

    @ManyToMany(mappedBy = "cashBackCategories")
    private Set<Account> accounts = new HashSet<>();

    @ManyToMany(mappedBy = "cashBackCategories", cascade = CascadeType.ALL)
    private Set<com.bank.example.model.cascade.CashBackCompany> cashBackCompanies = new HashSet<>();

    public CashBackCategory(String name) {
        this.name = name;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void addCashBackCompany(com.bank.example.model.cascade.CashBackCompany cashBackCompany) {
        cashBackCompanies.add(cashBackCompany);
        cashBackCompany.getCashBackCategories().add(this);
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
