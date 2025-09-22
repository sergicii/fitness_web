package com.fitness.enums;
import lombok.Getter;

@Getter
public enum ContractedState {
    EN_PROCESO("En proceso"),
    VENDIDO("Vendido");

    private final String value;

    ContractedState(String value) {
        this.value = value;
    }
}
