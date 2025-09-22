package com.fitness.enums;

import lombok.Getter;
@Getter
public enum IncidentState {
    SIN_REVISAR("No revisado"),
    REVISADO("Revisado");

    private final String value;

    IncidentState(String value) {
        this.value = value;
    }

}
