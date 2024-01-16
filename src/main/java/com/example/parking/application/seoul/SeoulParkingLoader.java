package com.example.parking.application.seoul;

import com.example.parking.application.ParkingLoader;
import com.example.parking.application.seoul.dto.SeoulCityParkingResponse.ParkingInfo.SeoulCityParking;
import com.example.parking.domain.parking.Parking;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SeoulParkingLoader implements ParkingLoader {

    private final SeoulParkingConnector seoulParkingConnector;
    private final SeoulParkingConverter seoulParkingConverter;

    @Override
    public void load() {
        List<SeoulCityParking> seoulCityParkingLots = seoulParkingConnector.connect();
        List<Parking> parkingLots = seoulParkingConverter.convert(seoulCityParkingLots);
        // db 저장
    }
}
