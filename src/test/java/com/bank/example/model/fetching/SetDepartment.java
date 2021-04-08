package com.bank.example.model.fetching;

import com.bank.example.model.Department;
import com.bank.example.model.Manager;
import com.bank.example.model.Operator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity(name = "SetDepartment")
@NoArgsConstructor
@Getter
@Setter
@Table(name = "department", schema = "public")
public class SetDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department")
    private Set<Operator> operators = new HashSet<>();

    @OneToMany(mappedBy = "department")
    private List<com.bank.example.model.Manager> managers = new ArrayList<>();

    public SetDepartment(String name) {
        this.name = name;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetDepartment that = (SetDepartment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
