package com.example.parking.application.parking;

import com.example.parking.domain.parking.Parking;
import com.example.parking.domain.parking.dto.ParkingQueryCondition;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ParkingDomainService {

    public List<Parking> filterByCondition(List<Parking> parkingLots, ParkingQueryCondition parkingQueryCondition) {
        return parkingLots.stream()
                .filter(parking -> parking.isMatchOperationType(parkingQueryCondition.getOperationType()))
                .filter(parking -> parking.isMatchParkingType(parkingQueryCondition.getParkingType()))
                .toList();
    }
}
