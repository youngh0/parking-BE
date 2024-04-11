package com.example.parkingscheduler.fake;

import com.example.parking.domain.parking.Parking;
import com.example.parking.support.exception.ClientException;
import com.example.parking.support.exception.ExceptionInformation;
import com.example.parkingscheduler.parkingapi.ParkingApiService;
import java.util.List;

public class ExceptionParkingApiService implements ParkingApiService {

    @Override
    public boolean offerCurrentParking() {
        return true;
    }

    @Override
    public List<Parking> read() {
        throw new ClientException(ExceptionInformation.INVALID_CONNECT);
    }
}
