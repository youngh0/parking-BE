package com.example.parkingscheduler.parkingapi;

import com.example.parking.domain.parking.Parking;
import java.util.List;

public interface ParkingApiService {

    default boolean offerCurrentParking() {
        return false;
    }

    List<Parking> read() throws Exception;
}
