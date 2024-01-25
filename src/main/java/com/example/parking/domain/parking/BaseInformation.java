package com.example.parking.domain.parking;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class BaseInformation {

    private String name;
    private String tel;
    private String address;

    @Embedded
    private PayTypes payType;

    @Enumerated(EnumType.STRING)
    private ParkingType parkingType;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    public BaseInformation(final String name, final String tel, final String address, final PayTypes payType,
                           final ParkingType parkingType,
                           final OperationType operationType) {
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.payType = payType;
        this.parkingType = parkingType;
        this.operationType = operationType;
    }
}
