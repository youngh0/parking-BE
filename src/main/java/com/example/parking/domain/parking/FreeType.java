package com.example.parking.domain.parking;

import java.util.Arrays;

public enum FreeType {

    FREE("무료"),
    PAY("유료"),
    NO_INFO("정보 없음");

    private final String description;

    FreeType(String description) {
        this.description = description;
    }

    public static FreeType find(String description) {
        return Arrays.stream(FreeType.values())
                .filter(freeType -> freeType.description.equals(description))
                .findAny()
                .orElse(NO_INFO);
    }
}
