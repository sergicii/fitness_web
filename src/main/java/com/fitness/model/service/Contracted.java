package com.fitness.model.service;

import com.fitness.enums.ContractedState;
import com.fitness.model.user.Client;
import com.fitness.model.user.Employee;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Contracted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate start;
    private LocalDate end;

    @Enumerated(EnumType.STRING)
    private ContractedState state;

    private String note;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    private Membership membership;
}
