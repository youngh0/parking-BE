package com.example.parking.domain.parking;

import jakarta.persistence.Embeddable;
import java.time.LocalTime;

@Embeddable
public class
TimeInfo {

    private LocalTime beginTime;
    private LocalTime endTime;

    protected TimeInfo() {
    }

    public TimeInfo(LocalTime beginTime, LocalTime endTime) {
        this.beginTime = beginTime;
        this.endTime = endTime;
    }
}
