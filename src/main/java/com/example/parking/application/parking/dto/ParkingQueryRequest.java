package com.example.parking.application.parking.dto;

import com.example.parking.domain.parking.OperationType;
import com.example.parking.domain.parking.ParkingType;
import com.example.parking.domain.parking.dto.ParkingQueryCondition;
import com.example.parking.domain.searchcondition.SearchConditionAvailable;
import lombok.Getter;

@Getter
public class ParkingQueryRequest {

    private final String operationType;
    private final String parkingType;
    private final String feeType;
    private final Boolean cardEnabled;
    private final String priority;
    private final Integer hours;
    private final Double latitude;
    private final Double longitude;
    private final Integer radius;

    public ParkingQueryRequest(String operationType, String parkingType, String feeType, Boolean cardEnabled,
                               String priority,
                               Integer hours, Double latitude, Double longitude, Integer radius) {
        this.operationType = operationType;
        this.parkingType = parkingType;
        this.feeType = feeType;
        this.cardEnabled = cardEnabled;
        this.priority = priority;
        this.hours = hours;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    public ParkingQueryCondition toQueryCondition() {
        return new ParkingQueryCondition(
                SearchConditionAvailable.find(this.operationType, OperationType.values()),
                ParkingType.find(this.parkingType),
                this.cardEnabled
        );
    }
}
