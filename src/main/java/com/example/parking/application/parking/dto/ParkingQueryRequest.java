package com.example.parking.application.parking.dto;

import lombok.Getter;

@Getter
public class ParkingQueryRequest {

    private final Double latitude;
    private final Double longitude;
    private final Integer radius;

    public ParkingQueryRequest(Double latitude, Double longitude, Integer radius) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }
}
