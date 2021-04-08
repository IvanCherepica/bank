package com.bank.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CashBackCompany {

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
    private List<CashBackCategory> cashBackCategories = new ArrayList<>();

    public void addAccount(Account account) {
        accounts.add(account);
        account.getCashBackCompanies().add(this);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
        account.getCashBackCompanies().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CashBackCompany that = (CashBackCompany) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
