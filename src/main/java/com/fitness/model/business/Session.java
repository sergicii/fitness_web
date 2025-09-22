package com.fitness.model.business;

import com.fitness.enums.SessionType;
import com.fitness.model.user.Employee;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Integer maxParticipants;
    private String sessionReport;
    @Enumerated(EnumType.STRING)
    private SessionType type;

    @ManyToOne
    private Training training;

    @ManyToOne
    private Site site;

    @ManyToOne
    private Employee trainer;

    @OneToMany(mappedBy = "session", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Reservation> reservations;
}
