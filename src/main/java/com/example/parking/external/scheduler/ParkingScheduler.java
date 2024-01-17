package com.example.parking.external.scheduler;

import com.example.parking.domain.parking.Parking;
import com.example.parking.external.parkingapi.ParkingStoreService;
import com.example.parking.external.parkingapi.busan.BusanParkingApiService;
import com.example.parking.external.parkingapi.seoul.SeoulParkingApiService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ParkingScheduler {

    private final ParkingStoreService parkingStoreService;
    private final SeoulParkingApiService seoulParkingApiService;
    private final BusanParkingApiService busanParkingApiService;

    public ParkingScheduler(ParkingStoreService parkingStoreService,
                            SeoulParkingApiService seoulParkingApiService,
                            BusanParkingApiService busanParkingApiService) {
        this.parkingStoreService = parkingStoreService;
        this.seoulParkingApiService = seoulParkingApiService;
        this.busanParkingApiService = busanParkingApiService;
    }

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    public void seoulBatch() {
        List<Parking> rows = seoulParkingApiService.read();
        parkingStoreService.store(rows);
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
    public void busanBatch() {
        List<Parking> rows = busanParkingApiService.read();
        parkingStoreService.store(rows);
    }
}
