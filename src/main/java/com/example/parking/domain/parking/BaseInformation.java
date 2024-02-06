package com.example.parking.domain.parking;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString(of = "name")
@Embeddable
public class BaseInformation {

    private String name;
    private String tel;
    private String address;

    @Embedded
    private PayTypes payTypes;

    @Enumerated(STRING)
    private ParkingType parkingType;

    @Enumerated(STRING)
    private OperationType operationType;

    public BaseInformation(String name, String tel, String address, PayTypes payTypes, ParkingType parkingType,
                           OperationType operationType) {
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.payTypes = payTypes;
        this.parkingType = parkingType;
        this.operationType = operationType;
    }

    public boolean isMatchOperationType(OperationType operationType) {
        return this.operationType == operationType;
    }

    public boolean isMatchParkingType(ParkingType parkingType) {
        return this.parkingType == parkingType;
    }
}
