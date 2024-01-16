package com.example.parking.application.busan;

import com.example.parking.application.ParkingLoader;
import com.example.parking.application.busan.dto.BusanParkingResponse.BusanParkingInfo.Body.Items.BusanParkingData;
import com.example.parking.domain.parking.Parking;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BusanParkingLoader implements ParkingLoader {

    private final BusanParkingConnector busanParkingConnector;
    private final BusanParkingConverter busanParkingConverter;

    @Override
    public void load() {
        List<BusanParkingData> busanParkingResponses = busanParkingConnector.connect();
        List<Parking> ParkingLots = busanParkingConverter.convert(busanParkingResponses);
    }
}
