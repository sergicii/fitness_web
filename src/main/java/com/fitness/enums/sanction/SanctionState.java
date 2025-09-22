package com.fitness.enums.sanction;

import lombok.Getter;

@Getter
public enum SanctionState {
    CUMPLIDA("Cumplido"),
    EN_PROCESO("En proceso");

    private final String value;

    SanctionState(String value) {
        this.value = value;
    }
}
