package com.example.parking.application.parking;

import com.example.parking.application.parking.dto.ParkingLotsResponse;
import com.example.parking.application.parking.dto.ParkingLotsResponse.ParkingResponse;
import com.example.parking.application.parking.dto.ParkingQueryRequest;
import com.example.parking.application.parking.dto.ParkingSearchConditionRequest;
import com.example.parking.domain.favorite.Favorite;
import com.example.parking.domain.favorite.FavoriteRepository;
import com.example.parking.domain.parking.FilterCondition;
import com.example.parking.domain.parking.Location;
import com.example.parking.domain.parking.OperationType;
import com.example.parking.domain.parking.Parking;
import com.example.parking.domain.parking.ParkingType;
import com.example.parking.domain.parking.PayType;
import com.example.parking.domain.parking.repository.ParkingRepository;
import com.example.parking.support.Association;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ParkingService {

    private static final String DISTANCE_ORDER_CONDITION = "가까운 순";

    private final ParkingRepository parkingRepository;
    private final ParkingDomainService parkingDomainService;
    private final FavoriteRepository favoriteRepository;

    @Transactional(readOnly = true)
    public ParkingLotsResponse findParkingLots(ParkingQueryRequest parkingQueryRequest,
                                               ParkingSearchConditionRequest parkingSearchConditionRequest,
                                               Long memberId) {
        Location destination = Location.of(parkingQueryRequest.getLongitude(), parkingQueryRequest.getLatitude());
        FilterCondition filterCondition = toFilterCondition(parkingSearchConditionRequest);

        List<Favorite> favorites = findMemberFavorites(memberId);
        List<Parking> parkingLots = findParkingLotsByOrderCondition(parkingSearchConditionRequest.getPriority(),
                parkingQueryRequest, destination);
        List<Parking> filteredParkingLots = filteringByCondition(parkingLots, filterCondition);

        List<ParkingResponse> parkingResponses = parkingDomainService.calculateParkingInfo(filteredParkingLots,
                parkingSearchConditionRequest.getHours(), destination, favorites);

        return new ParkingLotsResponse(parkingResponses);
    }

    private FilterCondition toFilterCondition(ParkingSearchConditionRequest parkingSearchConditionRequest) {
        List<ParkingType> parkingTypes = ParkingType.collectMatch(parkingSearchConditionRequest.getParkingTypes());
        List<OperationType> operationTypes = OperationType.collectMatch(
                parkingSearchConditionRequest.getOperationTypes());
        List<PayType> payTypes = PayType.collectMatch(parkingSearchConditionRequest.getPayTypes());

        return new FilterCondition(operationTypes, parkingTypes, payTypes);
    }

    private List<Favorite> findMemberFavorites(Long memberId) {
        if (memberId == null) {
            return Collections.emptyList();
        }
        return favoriteRepository.findByMemberId(Association.from(memberId));
    }

    private List<Parking> filteringByCondition(List<Parking> parkingLots, FilterCondition filterCondition) {
        return parkingDomainService.filterByCondition(parkingLots, filterCondition);
    }

    private List<Parking> findParkingLotsByOrderCondition(String priority, ParkingQueryRequest parkingQueryRequest,
                                                          Location middleLocation) {
        if (priority.equals(DISTANCE_ORDER_CONDITION)) {
            return parkingRepository.findAroundParkingLotsOrderByDistance(middleLocation.getPoint(),
                    parkingQueryRequest.getRadius());
        }
        return parkingRepository.findAroundParkingLots(middleLocation.getPoint(), parkingQueryRequest.getRadius());
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
