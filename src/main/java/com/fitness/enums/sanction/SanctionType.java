package com.fitness.enums.sanction;
import lombok.Getter;

@Getter
public enum SanctionType {
    LEVE("Leve"),
    SEVERA("Severa"),
    GRAVE("Grave");

    private final String value;
    SanctionType(String value) {
        this.value = value;
    }
}
