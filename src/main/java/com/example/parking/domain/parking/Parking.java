package com.example.parking.domain.parking;

import com.example.parking.domain.AuditingEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Parking extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private BaseInformation baseInformation;

    @Embedded
    private Location location;

    @Embedded
    private Space space;

    @Embedded
    private FreeOperatingTime freeOperatingTime;

    @Embedded
    private OperatingTime operatingTime;

    @Embedded
    private FeePolicy feePolicy;

    @Builder
    private Parking(Long id, BaseInformation baseInformation, Location location, Space space,
                    FreeOperatingTime freeOperatingTime, OperatingTime operatingTime, FeePolicy feePolicy) {
        this.id = id;
        this.baseInformation = baseInformation;
        this.location = location;
        this.space = space;
        this.freeOperatingTime = freeOperatingTime;
        this.operatingTime = operatingTime;
        this.feePolicy = feePolicy;
    }

    public Parking(BaseInformation baseInformation, Location location, Space space,
                   FreeOperatingTime freeOperatingTime, OperatingTime operatingTime, FeePolicy feePolicy) {
        this.baseInformation = baseInformation;
        this.location = location;
        this.space = space;
        this.freeOperatingTime = freeOperatingTime;
        this.operatingTime = operatingTime;
        this.feePolicy = feePolicy;
    }

    public int calculatePayOfChargeMinutes(DayParking dayParking) {
        return freeOperatingTime.calculateNonFreeUsageMinutes(dayParking);
    }

    public Fee calculateParkingFee(int payOfChargeMinutes) {
        return feePolicy.calculateFee(payOfChargeMinutes);
    }

    public boolean supportCalculateParkingFee() {
        return feePolicy.supportBase() && feePolicy.supportExtra();
    }

    public void update(Parking updated) {
        this.space = updated.space;
        this.freeOperatingTime = updated.freeOperatingTime;
        this.operatingTime = updated.operatingTime;
        this.feePolicy = updated.feePolicy;
    }

    public void update(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Parking parking = (Parking) o;
        return Objects.equals(id, parking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isMatchOperationType(OperationType operationType) {
        return baseInformation.isMatchOperationType(operationType);
    }

    public boolean isMatchParkingType(ParkingType parkingType) {
        return baseInformation.isMatchParkingType(parkingType);
    }
}
