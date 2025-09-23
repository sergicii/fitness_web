package com.fitness.model.business;

import com.fitness.enums.SessionType;
import com.fitness.model.user.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
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
    @ToString.Exclude
    private List<Reservation> reservations = new ArrayList<>();
}
