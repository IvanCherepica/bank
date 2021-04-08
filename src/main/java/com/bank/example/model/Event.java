package com.bank.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_generator")
    @SequenceGenerator(sequenceName = "event_sequence", name = "event_generator", allocationSize = 10)
    private Long id;

    private String name;

    private LocalDateTime dateTime;

//    @OneToMany(mappedBy = "event")
//    private List<Employee> employees = new ArrayList<>();
}
