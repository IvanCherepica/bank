package com.bank.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String PIN;

    private String CVV;

    private Double sum = 0.0;

    private Boolean isDefaultCard = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    public void addSum(Double sum) {
        this.sum += sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id) &&
                Objects.equals(PIN, card.PIN) &&
                Objects.equals(CVV, card.CVV);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, PIN, CVV);
    }
}
