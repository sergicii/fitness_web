package com.fitness.enums;
import lombok.Getter;

@Getter
public enum UserRol {
    CLIENT("Cliente"),
    ADMIN("Administrador"),
    EMPLOYEE("Empleado");

    private final String value;

    UserRol(String value) {
        this.value = value;
    }
}