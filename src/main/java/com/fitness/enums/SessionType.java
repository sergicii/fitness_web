package com.fitness.enums;
import lombok.Getter;

@Getter
public enum SessionType {
    PERSONALIZADA("Personalizada"),
    GRUPAL("Grupal");

    private final String value;

    SessionType(String value) {
        this.value = value;
    }
}
