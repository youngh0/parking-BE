package com.example.parking.domain.parking;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum Type {
    OFF_STREET("노외"),
    ON_STREET("노상"),
    MECHANICAL("기계식");

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
