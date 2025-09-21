package com.fitness.model.user;

import com.fitness.enums.EmployeeArea;
import com.fitness.model.business.Session;
import com.fitness.model.monitoring.Incident;
import com.fitness.model.service.Contracted;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Employee extends Person {
    @Enumerated(EnumType.STRING)
    private EmployeeArea area;

    @OneToMany(mappedBy = "trainer")
    private List<Session> sessions;

    @OneToMany(mappedBy = "employee")
    private List<Incident> incidents;

    @OneToMany(mappedBy = "seller")
    private List<Contracted> contracted;
}
