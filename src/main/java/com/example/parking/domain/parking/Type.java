package com.example.parking.domain.parking;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum Type {
    OFF_STREET("노외 주차장"),
    ON_STREET("노상 주차장"),
    MECHANICAL("기계식 주차장");

    private static final Map<String, Type> descriptions =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(Type::getDescription, Function.identity())));

    private final String description;

    Type(String description) {
        this.description = description;
    }

    public static Type find(String description) {
        return descriptions.get(description);
    }
}
