package com.example.parking.domain.parking;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class OperatingTime {

    @AttributeOverrides({
            @AttributeOverride(name = "beginTime", column = @Column(name = "weekday_begin_time")),
            @AttributeOverride(name = "endTime", column = @Column(name = "weekday_end_time"))
    })
    @Embedded
    private TimeInfo weekday;

    @AttributeOverrides({
            @AttributeOverride(name = "beginTime", column = @Column(name = "weekend_begin_time")),
            @AttributeOverride(name = "endTime", column = @Column(name = "weekend_end_time"))
    })
    @Embedded
    private TimeInfo weekend;

    @AttributeOverrides({
            @AttributeOverride(name = "beginTime", column = @Column(name = "holiday_begin_time")),
            @AttributeOverride(name = "endTime", column = @Column(name = "holiday_end_time"))
    })
    @Embedded
    private TimeInfo holiday;

    protected OperatingTime() {
    }

    public OperatingTime(TimeInfo weekday,
                         TimeInfo weekend,
                         TimeInfo holiday) {
        this.weekday = weekday;
        this.weekend = weekend;
        this.holiday = holiday;
    }
}
