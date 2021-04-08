package com.bank.example.model.fetching;

import com.bank.example.model.Department;
import jdk.nashorn.internal.ir.annotations.Immutable;
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
import java.util.Objects;

@Entity(name = "ImmutableManager")
@NoArgsConstructor
@Getter
@Setter
@Table(name = "manager", schema = "public")
@Immutable
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;

    private String passportNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    public Manager(String phone, String passportNumber) {
        this.phone = phone;
        this.passportNumber = passportNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return Objects.equals(id, manager.id) &&
                Objects.equals(phone, manager.phone) &&
                Objects.equals(passportNumber, manager.passportNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phone, passportNumber);
    }
}