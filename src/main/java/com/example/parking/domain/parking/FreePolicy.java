package com.example.parking.domain.parking;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class FreePolicy {

    private boolean isFree;
    private boolean isSaturdayFree;
    private boolean isHolidayFree;

    public FreePolicy(final boolean isFree, final boolean isSaturdayFree, final boolean isHolidayFree) {
        this.isFree = isFree;
        this.isSaturdayFree = isSaturdayFree;
        this.isHolidayFree = isHolidayFree;
    }
}
