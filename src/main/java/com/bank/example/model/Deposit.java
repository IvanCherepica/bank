package com.bank.example.model;

import com.bank.example.model.operation.Interests;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate openDate;

    private LocalDate closeDate;

    private Float rate;

    private Double sum;

    @OneToMany(mappedBy = "deposit")
    private List<Interests> interests;

    public Account getAccount() {
        return null;
    }

    public void setAccount(Account account) {

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
