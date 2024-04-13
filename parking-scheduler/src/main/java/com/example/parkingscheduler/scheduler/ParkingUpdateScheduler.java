package com.example.parkingscheduler.scheduler;

import com.example.parking.domain.parking.Location;
import com.example.parking.domain.parking.Parking;
import com.example.parking.domain.parking.ParkingRepository;
import com.example.parkingscheduler.coordinate.CoordinateService;
import com.example.parkingscheduler.parkingapi.ParkingApiService;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ParkingUpdateScheduler {

    private final List<ParkingApiService> parkingApiServices;
    private final ParkingMapper parkingMapper;
    private final ParkingService parkingService;
    private final ParkingRepository parkingRepository;
    private final CoordinateService coordinateService;

    @Scheduled(cron = "0/10 * * * * ?")
    public void autoUpdateOfferCurrentParking() {
        Map<String, Parking> parkingLots = readBy(ParkingApiService::offerCurrentParking);
        Map<String, Parking> saved = findAllByName(parkingLots.keySet());
        List<Parking> newParkingLots = findNewParkingLots(parkingLots, saved);
        parkingService.updateParkingLots(parkingLots, saved, newParkingLots);
    }

    private Map<String, Parking> readBy(Predicate<ParkingApiService> currentParkingAvailable) {
        return parkingApiServices.stream()
                .filter(currentParkingAvailable)
                .map(this::read)
                .flatMap(Collection::stream)
                .collect(parkingMapper.toParkingMap());
    }

    private List<Parking> read(ParkingApiService parkingApiService) {
        try {
            return parkingApiService.read();
        } catch (Exception e) {
            log.warn("Error while converting {} to Parking {}", parkingApiService.getClass(), e.getMessage());
            return Collections.emptyList();
        }
    }

    private Map<String, Parking> findAllByName(Set<String> names) {
        return parkingRepository.findAllByBaseInformationNameIn(names)
                .stream()
                .collect(parkingMapper.toParkingMap());
    }

    private void updateLocation(List<Parking> newParkingLots) {
        for (Parking parking : newParkingLots) {
            Location locationByAddress = coordinateService.extractLocationByAddress(
                    parking.getBaseInformation().getAddress(),
                    parking.getLocation());
            parking.update(locationByAddress);
        }
    }

    private List<Parking> findNewParkingLots(Map<String, Parking> parkingLots, Map<String, Parking> saved) {
        List<Parking> newParkingLots = parkingLots.keySet()
                .stream()
                .filter(parkingName -> !saved.containsKey(parkingName))
                .map(parkingLots::get)
                .toList();
        updateLocation(newParkingLots);
        return newParkingLots;
    }

    @Scheduled(fixedRate = 30, timeUnit = TimeUnit.DAYS)
    public void autoUpdateNotOfferCurrentParking() {
        Map<String, Parking> parkingLots = readBy(parkingApiService -> !parkingApiService.offerCurrentParking());
        Map<String, Parking> saved = findAllByName(parkingLots.keySet());
        List<Parking> newParkingLots = findNewParkingLots(parkingLots, saved);
        parkingService.updateParkingLots(parkingLots, saved, newParkingLots);
    }
}
