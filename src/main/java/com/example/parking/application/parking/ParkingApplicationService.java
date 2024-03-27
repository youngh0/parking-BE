package com.example.parking.application.parking;

import com.example.parking.application.parking.dto.ParkingLotsResponse.ParkingResponse;
import com.example.parking.domain.favorite.Favorite;
import com.example.parking.domain.parking.Fee;
import com.example.parking.domain.parking.FilterCondition;
import com.example.parking.domain.parking.Location;
import com.example.parking.domain.parking.Parking;
import com.example.parking.domain.parking.ParkingFeeCalculator;
import com.example.parking.domain.searchcondition.FeeType;
import com.example.parking.support.Association;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ParkingApplicationService {

    private final ParkingFeeCalculator parkingFeeCalculator;

    public List<Parking> filterByCondition(List<Parking> parkingLots, FilterCondition filterCondition, int hours,
                                           LocalDateTime now) {
        return parkingLots.stream()
                .filter(parking -> checkFeeTypeIsPaid(filterCondition) || checkFeeTypeAndFeeFree(filterCondition, now,
                        hours, parking))
                .filter(parking -> parking.containsOperationType(filterCondition.getOperationTypes()))
                .filter(parking -> parking.containsParkingType(filterCondition.getParkingTypes()))
                .filter(parking -> parking.containsPayType(filterCondition.getPayTypes()))
                .toList();
    }

    private boolean checkFeeTypeIsPaid(FilterCondition filterCondition) {
        return filterCondition.getFeeType() == FeeType.PAID;
    }

    private boolean checkFeeTypeAndFeeFree(FilterCondition filterCondition, LocalDateTime now, int hours,
                                           Parking parking) {
        return filterCondition.getFeeType() == FeeType.FREE && parkingFeeCalculator.calculateParkingFee(parking, now,
                now.plusHours(hours)).isFree();
    }

    public List<ParkingResponse> collectParkingInfo(List<Parking> parkingLots, int hours,
                                                    Location destination, List<Favorite> memberFavorites) {
        Set<Long> favoriteParkingIds = extractFavoriteParkingIds(memberFavorites);
        return calculateParkingInfo(parkingLots, destination, hours, favoriteParkingIds);
    }

    private List<ParkingResponse> calculateParkingInfo(List<Parking> parkingLots, Location destination, int hours,
                                                       Set<Long> favoriteParkingIds) {
        LocalDateTime now = LocalDateTime.now();

        List<ParkingResponse> parkingResponses = new ArrayList<>();
        for (Parking parking : parkingLots) {
            Fee fee = parkingFeeCalculator.calculateParkingFee(parking, now, now.plusHours(hours));
            int walkingTime = parking.calculateWalkingTime(destination);
            ParkingResponse parkingResponse = toParkingResponse(
                    parking,
                    fee,
                    walkingTime,
                    favoriteParkingIds.contains(parking.getId())
            );
            parkingResponses.add(parkingResponse);
        }
        return parkingResponses;
    }

    private Set<Long> extractFavoriteParkingIds(List<Favorite> memberFavorites) {
        return memberFavorites.stream()
                .map(Favorite::getParkingId)
                .map(Association::getId)
                .collect(Collectors.toSet());
    }

    private ParkingResponse toParkingResponse(Parking parking, Fee fee, int walkingTime, boolean isFavorite) {
        return new ParkingResponse(
                parking.getId(),
                parking.getBaseInformation().getName(),
                fee.getFee(),
                walkingTime,
                parking.getBaseInformation().getParkingType().getDescription(),
                isFavorite,
                parking.getLocation().getLatitude(),
                parking.getLocation().getLongitude()
        );
    }
}
