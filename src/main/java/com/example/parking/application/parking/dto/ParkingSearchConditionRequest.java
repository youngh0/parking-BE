package com.example.parking.application.parking.dto;

import com.example.parking.domain.parking.OperationType;
import com.example.parking.domain.parking.ParkingType;
import com.example.parking.domain.parking.PayType;
import com.example.parking.domain.searchcondition.FeeType;
import java.util.List;
import lombok.Getter;

@Getter
public class ParkingSearchConditionRequest {

    private static final int BASE_HOURS = 1;
    private static final String RECOMMEND_ORDER_CONDITION = "추천 순";

    private final List<String> operationTypes;
    private final List<String> parkingTypes;
    private final List<String> feeTypes;
    private final List<String> payTypes;
    private final Integer hours;
    private final String priority;

    public ParkingSearchConditionRequest(List<String> operationTypes, List<String> parkingTypes, List<String> feeTypes,
                                         List<String> payTypes, int hours, String priority) {
        this.operationTypes = operationTypes;
        this.parkingTypes = parkingTypes;
        this.feeTypes = feeTypes;
        this.payTypes = payTypes;
        this.hours = hours;
        this.priority = priority;
    }

    public static ParkingSearchConditionRequest base() {
        return new ParkingSearchConditionRequest(
                OperationType.getAllValues(),
                ParkingType.getAllValues(),
                FeeType.getAllValues(),
                PayType.getAllValues(),
                BASE_HOURS,
                RECOMMEND_ORDER_CONDITION
        );
    }
}
