package com.example.parking.application;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 1. 외부 api 호출한다. -> Connector 2. 외부 api 의 결과값을 List<Parking> 으로 변환한다. -> SeoulParkingConverter
 * <p>
 * ParkingBridge -> connect() -> convert()
 */

@RequiredArgsConstructor
@Getter
@Component
public class ParkingScheduler {

    private final List<ParkingLoader> parkingLoaders;

    public void linkParking() {
//        parkingLoaders.stream()
    }
}
