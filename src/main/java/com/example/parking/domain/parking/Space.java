package com.example.parking.domain.parking;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Space {

    private Boolean currentParkingProviding;
    private Integer capacity;
    private Integer currentParking;

    public Space(final Boolean currentParkingProviding, final Integer capacity, final Integer currentParking) {
        this.currentParkingProviding = currentParkingProviding;
        this.capacity = capacity;
        this.currentParking = currentParking;
    }
}
