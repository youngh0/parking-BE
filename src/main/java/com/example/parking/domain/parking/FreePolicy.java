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

    public int calculateMinutes(int weekdayMinutes, int saturdayMinutes, int holidayMinutes) {
        if (isFree) {
            return 0;
        }
        if (isSaturdayFree && isHolidayFree) {
            return weekdayMinutes;
        }
        if (isSaturdayFree) {
            return weekdayMinutes + holidayMinutes;
        }
        if (isHolidayFree) {
            return weekdayMinutes + saturdayMinutes;
        }
        return weekdayMinutes + saturdayMinutes + holidayMinutes;
    }
}
