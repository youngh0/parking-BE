package com.example.parking.external.parkingapi;

import com.example.parking.domain.parking.Parking;
import com.example.parking.domain.parking.ParkingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ParkingStoreService {

    private final ParkingRepository parkingRepository;

    public ParkingStoreService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    public void store(List<Parking> parkings) {
        for (Parking parking : parkings) {
            /*
                    exist -> update
                    or    -> persist
             */
        }
    }
}
