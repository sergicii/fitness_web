package com.fitness.model.monitoring;

import com.fitness.enums.sanction.SanctionState;
import com.fitness.enums.sanction.SanctionType;
import com.fitness.model.user.Client;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Sanction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate start;
    private LocalDate end;

    @Enumerated(EnumType.STRING)
    private SanctionType type;

    @Enumerated(EnumType.STRING)
    private SanctionState state;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;
}
