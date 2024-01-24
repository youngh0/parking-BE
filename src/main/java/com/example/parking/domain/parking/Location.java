package com.example.parking.domain.parking;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class Location {

    private String longitude;
    private String latitude;

    public Location(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
