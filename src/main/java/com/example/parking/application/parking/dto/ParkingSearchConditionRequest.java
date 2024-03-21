package com.example.parking.application.parking.dto;

import com.example.parking.domain.parking.OperationType;
import com.example.parking.domain.parking.ParkingType;
import com.example.parking.domain.parking.PayType;
import com.example.parking.domain.searchcondition.FeeType;
import java.util.List;

public class ParkingSearchConditionRequest {

    private static final ParkingSearchConditionRequest BASE = new ParkingSearchConditionRequest(
            OperationType.getAllValues(),
            ParkingType.getAllValues(),
            FeeType.getAllValues(),
            PayType.getAllValues(),
            1
    );

    private final List<String> operationTypes;
    private final List<String> parkingTypes;
    private final List<String> feeTypes;
    private final List<String> payTypes;
    private final int hours;

    public ParkingSearchConditionRequest(List<String> operationTypes, List<String> parkingTypes, List<String> feeTypes,
                                         List<String> payTypes, int hours) {
        this.operationTypes = operationTypes;
        this.parkingTypes = parkingTypes;
        this.feeTypes = feeTypes;
        this.payTypes = payTypes;
        this.hours = hours;
    }

    public static ParkingSearchConditionRequest base() {
        return BASE;
    }
}
