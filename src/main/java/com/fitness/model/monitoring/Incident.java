package com.fitness.model.monitoring;

import com.fitness.enums.IncidentState;
import com.fitness.model.user.Client;
import com.fitness.model.user.Employee;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private IncidentState state;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;
}
