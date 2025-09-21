package com.fitness.model.business;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private Training training;

    @ManyToOne(fetch = FetchType.LAZY)
    private Site site;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee trainer;

    @OneToMany(mappedBy = "session")
    private List<Reservation> reservations;
}
