package com.fitness.enums;
import lombok.Getter;

@Getter
public enum Gender {
    MASCULINO("Masculino"),
    FEMENINO("Femenino");

    private final String value;

    Gender(String value) {
        this.value = value;
    }
}
