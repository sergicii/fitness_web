package com.fitness.enums;

import lombok.Getter;

@Getter
public enum EmployeeArea {
    TRAINERS ("Area de entrenadores"),
    VENTAS ("Area comercial"),
    IT ("Area IT"),
    OPERACIONES ("Area de operaciones");

    private final String value;

    EmployeeArea(String value) {
        this.value = value;
    }
}
