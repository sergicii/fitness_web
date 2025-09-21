package com.fitness.model.business;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Duration duration;
    private Integer maxParticipants;

    @OneToMany(mappedBy = "training")
    private List<Session> sessions;
}
