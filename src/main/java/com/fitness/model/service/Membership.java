package com.fitness.model.service;

import com.fitness.enums.MemberShipCategory;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;

@Data
@NoArgsConstructor
@Entity
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MemberShipCategory category;

    private Duration duration;
    private Integer sessionPersonalized;
    private Integer sessionGroup;
    private BigDecimal price;
}
