package com.example.parking.domain.parking;

import com.example.parking.domain.searchcondition.FeeType;
import java.util.List;
import lombok.Getter;

@Getter
public class FilterCondition {

    private final List<OperationType> operationTypes;
    private final List<ParkingType> parkingTypes;
    private final List<PayType> payTypes;
    private final FeeType feeType;

    public FilterCondition(List<OperationType> operationTypes, List<ParkingType> parkingTypes, List<PayType> payTypes,
                           FeeType feeType) {
        this.operationTypes = operationTypes;
        this.parkingTypes = parkingTypes;
        this.payTypes = payTypes;
        this.feeType = feeType;
    }
}
