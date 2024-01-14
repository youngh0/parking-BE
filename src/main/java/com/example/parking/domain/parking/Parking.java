package com.example.parking.domain.parking;

import com.example.parking.domain.AuditingEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Parking extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private BaseInformation baseInformation;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Embedded
    private Location location;

    @Embedded
    private Space space;

    @Embedded
    private OperatingTime operatingTime;

    @Embedded
    private FeePolicy feePolicy;

    @Embedded
    private FreePolicy freePolicy;

    public Parking(BaseInformation baseInformation, Type type, Location location, Space space,
                   OperatingTime operatingTime, FeePolicy feePolicy, FreePolicy freePolicy) {
        this.baseInformation = baseInformation;
        this.type = type;
        this.location = location;
        this.space = space;
        this.operatingTime = operatingTime;
        this.feePolicy = feePolicy;
        this.freePolicy = freePolicy;
    }
}
