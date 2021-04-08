package com.bank.example.model.fetching;

import com.bank.example.model.Manager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

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


@Entity(name = "BatchSizeManagerDepartment")
@NoArgsConstructor
@Getter
@Setter
@Table(name = "department", schema = "public")
public class BatchDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_generator")
    @SequenceGenerator(sequenceName = "department_sequence", name = "department_generator", allocationSize = 10)
    private Long id;

    private String name;

    @BatchSize(size = 5)
    @OneToMany(mappedBy = "department", cascade = CascadeType.REMOVE)
    private List<Manager> managers = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatchDepartment that = (BatchDepartment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
