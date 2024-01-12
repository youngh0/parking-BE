package com.example.parking.domain.parking;

import jakarta.persistence.Embeddable;

@Embeddable
public class Location {

    private String longitude;
    private String latitude;

    protected Location() {
    }

    public Location(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
