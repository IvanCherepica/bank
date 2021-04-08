package com.bank.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class DocumentScans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String passport;

    private String ITN;

    private String insuranceNumber;

    @OneToOne(fetch = FetchType.LAZY)
    private Account account;

    public DocumentScans() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentScans that = (DocumentScans) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(passport, that.passport) &&
                Objects.equals(ITN, that.ITN) &&
                Objects.equals(insuranceNumber, that.insuranceNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, passport, ITN, insuranceNumber);
    }
}
