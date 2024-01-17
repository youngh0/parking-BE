package com.example.parking.external.parkingapi.busan;

import com.example.parking.domain.parking.Parking;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class BusanParkingApiService {

    public List<Parking> read() {
        return Collections.emptyList();
    }
}
