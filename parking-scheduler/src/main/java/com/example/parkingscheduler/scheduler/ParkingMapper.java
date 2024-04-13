package com.example.parkingscheduler.scheduler;

import com.example.parking.domain.parking.Parking;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ParkingMapper {

    public Collector<Parking, ?, Map<String, Parking>> toParkingMap() {
        return Collectors.toMap(
                parking -> parking.getBaseInformation().getName(),
                Function.identity(),
                (existing, replacement) -> existing
        );
    }
}
