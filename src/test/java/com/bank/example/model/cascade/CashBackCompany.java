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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "cascadeCashBackCompany")
@Table(name = "cash_back_company", schema = "public")
public class CashBackCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Manager uploader;

    @ManyToOne(fetch = FetchType.LAZY)
    private Manager editor;

    @ManyToMany(mappedBy = "cashBackCompanies")
    private Set<Account> accounts = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<com.bank.example.model.cascade.CashBackCategory> cashBackCategories = new HashSet<>();

    public CashBackCompany(String name) {
        this.name = name;
    }

    public void addAccount(Account account) {
        accounts.add(account);
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
