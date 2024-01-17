package com.example.parking.application;

import com.example.parking.domain.parking.Parking;
import com.example.parking.domain.parking.repository.ParkingRepository;
import java.util.Collection;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ParkingApiScheduler {

    private final List<ParkingAdapter> parkingAdapters;
    private final ParkingRepository parkingRepository;

    public ParkingApiScheduler(final List<ParkingAdapter> parkingAdapters, final ParkingRepository parkingRepository) {
        this.parkingAdapters = parkingAdapters;
        this.parkingRepository = parkingRepository;
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void autoUpdate() {
        final List<Parking> parkingLots = parkingAdapters.stream()
                .map(ParkingAdapter::convert)
                .flatMap(Collection::stream)
                .toList();

        log.info("parkingLots = {}", parkingLots);
        log.info("parkingLots.size() = {}", parkingLots.size());

        parkingRepository.saveAll(parkingLots);
    }
}
