package com.example.parking.domain.parking;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimeUnit {

    private int timeUnit;

    private TimeUnit(int timeUnit) {
        this.timeUnit = timeUnit;
    }

    public static TimeUnit from(int timeUnit) {
        return new TimeUnit(timeUnit);
    }
}
