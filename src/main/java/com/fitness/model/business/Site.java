package com.fitness.model.business;

import com.fitness.enums.SiteType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private SiteType type;

    private Integer capacity;
    private LocalTime opening;
    private LocalTime closing;

    @OneToMany(mappedBy = "site")
    private List<Session> sessions;
}
