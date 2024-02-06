package com.example.parking.domain.parking.dto;

import com.example.parking.domain.parking.OperationType;
import com.example.parking.domain.parking.ParkingType;
import lombok.Getter;

@Getter
public class ParkingQueryCondition {

    private OperationType operationType;
    private ParkingType parkingType;
    private Boolean cardEnabled;

    public ParkingQueryCondition(OperationType operationType, ParkingType parkingType, Boolean cardEnabled) {
        this.operationType = operationType;
        this.parkingType = parkingType;
        this.cardEnabled = cardEnabled;
    }
}
