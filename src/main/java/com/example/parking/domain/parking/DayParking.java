package com.example.parking.domain.parking;

public class DayParking {

    private int minutes;
    private Day day;

    public DayParking(int minutes, Day day) {
        this.minutes = minutes;
        this.day = day;
    }

    public int getMinutes() {
        return minutes;
    }

    public Day getDay() {
        return day;
    }
}
