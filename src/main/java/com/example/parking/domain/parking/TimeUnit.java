package com.example.parking.domain.parking;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimeUnit {

    private int timeUnit;

    public TimeUnit(final int timeUnit) {
        this.timeUnit = timeUnit;
    }
}
