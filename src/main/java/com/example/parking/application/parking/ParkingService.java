package com.example.parking.application.parking;

import com.example.parking.application.parking.dto.ParkingLotsResponse;
import com.example.parking.application.parking.dto.ParkingLotsResponse.ParkingResponse;
import com.example.parking.application.parking.dto.ParkingQueryRequest;
import com.example.parking.domain.parking.Location;
import com.example.parking.domain.parking.Parking;
import com.example.parking.domain.parking.dto.ParkingQueryCondition;
import com.example.parking.domain.parking.repository.ParkingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public ParkingLotsResponse findParkingLots(ParkingQueryRequest parkingQueryRequest) {
        Location middlLocation = Location.of(parkingQueryRequest.getLatitude(), parkingQueryRequest.getLongitude());

        List<Parking> parkingLots = findParkingLotsByOrderCondition(parkingQueryRequest, middlLocation);
        ParkingQueryCondition queryCondition = parkingQueryRequest.toQueryCondition();

        List<ParkingResponse> parkingLotsResponse = parkingLots.stream()
                .filter(parking -> parking.isMatchOperationType(queryCondition.getOperationType()))
                .filter(parking -> parking.isMatchParkingType(queryCondition.getParkingType()))
                .map(this::toParkingResponse)
                .toList();

        return new ParkingLotsResponse(parkingLotsResponse);
    }

    private List<Parking> findParkingLotsByOrderCondition(ParkingQueryRequest parkingQueryRequest,
                                                          Location middleLocation) {
        if (parkingQueryRequest.getPriority().equals("가까운 순")) {
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
}
