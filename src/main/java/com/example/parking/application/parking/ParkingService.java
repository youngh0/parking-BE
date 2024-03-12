package com.example.parking.application.parking;

import com.example.parking.application.parking.dto.ParkingLotsResponse;
import com.example.parking.application.parking.dto.ParkingLotsResponse.ParkingResponse;
import com.example.parking.application.parking.dto.ParkingQueryRequest;
import com.example.parking.domain.parking.Location;
import com.example.parking.domain.parking.Parking;
import com.example.parking.domain.parking.dto.ParkingQueryCondition;
import com.example.parking.domain.parking.repository.ParkingRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ParkingService {

    public static final String DISTANCE_ORDER_CONDITION = "가까운 순";
    private final ParkingRepository parkingRepository;
    private final ParkingDomainService parkingDomainService;

    public ParkingLotsResponse findParkingLots(ParkingQueryRequest parkingQueryRequest) {
        Location middlLocation = Location.of(parkingQueryRequest.getLatitude(), parkingQueryRequest.getLongitude());

        List<Parking> parkingLots = findParkingLotsByOrderCondition(parkingQueryRequest, middlLocation);
        List<ParkingResponse> parkingLotsResponse = filteringByCondition(parkingQueryRequest, parkingLots);

        return new ParkingLotsResponse(parkingLotsResponse);
    }

    private List<ParkingResponse> filteringByCondition(ParkingQueryRequest parkingQueryRequest,
                                                       List<Parking> parkingLots) {
        ParkingQueryCondition queryCondition = parkingQueryRequest.toQueryCondition();

        List<Parking> filteredParkingLots = parkingDomainService.filterByCondition(parkingLots, queryCondition);
        return filteredParkingLots.stream()
                .map(this::toParkingResponse)
                .toList();
    }

    private List<Parking> findParkingLotsByOrderCondition(ParkingQueryRequest parkingQueryRequest,
                                                          Location middleLocation) {
        if (parkingQueryRequest.getPriority().equals(DISTANCE_ORDER_CONDITION)) {
            return parkingRepository.findAroundParkingLotsOrderByDistance(middleLocation.getPoint(),
                    parkingQueryRequest.getRadius());
        }
        return parkingRepository.findAroundParkingLots(middleLocation.getPoint(), parkingQueryRequest.getRadius());
    }

    private ParkingResponse toParkingResponse(Parking parking) {
        return new ParkingResponse(
                parking.getId(),
                parking.getBaseInformation().getName(),
                null,
                null,
                parking.getBaseInformation().getParkingType().getDescription(),
                false,
                parking.getLocation().getLatitude(),
                parking.getLocation().getLongitude()
        );
    }
    @Transactional
    public void saveAll(List<Parking> parkingLots) {
        parkingRepository.saveAll(parkingLots);
    }

    @Transactional(readOnly = true)
    public Set<Parking> getParkingLots(Set<String> parkingNames) {
        return parkingRepository.findAllByBaseInformationNameIn(parkingNames);
    }
}
