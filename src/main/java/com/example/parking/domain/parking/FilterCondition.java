package com.example.parking.domain.parking;

import java.util.List;
import lombok.Getter;

@Getter
public class FilterCondition {

    private final List<OperationType> operationTypes;
    private final List<ParkingType> parkingTypes;
    private final List<PayType> payTypes;

    public FilterCondition(List<OperationType> operationTypes, List<ParkingType> parkingTypes, List<PayType> payTypes) {
        this.operationTypes = operationTypes;
        this.parkingTypes = parkingTypes;
        this.payTypes = payTypes;
    }
}
