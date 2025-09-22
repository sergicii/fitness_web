package com.fitness.enums;

import lombok.Getter;

@Getter
public enum SiteType {
    AIRE_LIBRE("Aire libre"),
    LOCAL("Local");

    private final String value;

    SiteType(String value) {
        this.value = value;
    }
}
