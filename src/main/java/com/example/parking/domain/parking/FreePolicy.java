package com.example.parking.domain.parking;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class FreePolicy {

    private FreeType isFree;
    private FreeType isSaturdayFree;
    private FreeType isHolidayFree;

    public FreePolicy(FreeType isFree, FreeType isSaturdayFree, FreeType isHolidayFree) {
        this.isFree = isFree;
        this.isSaturdayFree = isSaturdayFree;
        this.isHolidayFree = isHolidayFree;
    }
}
