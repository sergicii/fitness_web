package com.fitness.model.business;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;

@Getter
@Setter
@Builder
@AllArgsConstructor
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
    private Boolean state;
}
