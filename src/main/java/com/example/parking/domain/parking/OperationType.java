package com.example.parking.domain.parking;

import java.util.Arrays;

public enum OperationType {

    PUBLIC("공영"),
    PRIVATE("민영"),
    NO_INFO("정보 없음");

    private final String description;

    OperationType(String description) {
        this.description = description;
    }

    public static OperationType find(String description) {
        return Arrays.stream(OperationType.values())
                .filter(operationType -> operationType.description.equals(description))
                .findAny()
                .orElse(NO_INFO);
    }
}
