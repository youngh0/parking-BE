package com.example.parking.domain.parking;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class ParkingId {

    private Long parkingId;

    public ParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }
}
