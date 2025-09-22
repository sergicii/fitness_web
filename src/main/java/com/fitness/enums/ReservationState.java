package com.fitness.enums;

import lombok.Getter;

@Getter
public enum ReservationState {
    FALTA ("Falta"),
    ASISTENCIA("Asistencia");

    private final String value;
    ReservationState(String value) {
        this.value = value;
    }
}
