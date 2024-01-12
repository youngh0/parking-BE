package com.example.parking.domain.parking;

public enum Type {
    OFF_STREET("노외 주차장"),
    ON_STREET("노상 주차장"),
    MECHANICAL("기계식 주차장");

    private final String name;

    Type(String name) {
        this.name = name;
    }
}
