package com.bank.example.model.fetching;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "EagerManagerDepartment")
@NoArgsConstructor
@Getter
@Setter
@Table(name = "department", schema = "public")
public class EagerDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_generator")
    @SequenceGenerator(sequenceName = "department_sequence", name = "department_generator", allocationSize = 10)
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "department", cascade = CascadeType.REMOVE)
    private List<com.bank.example.model.Manager> managers = new ArrayList<>();

    public EagerDepartment(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EagerDepartment that = (EagerDepartment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
