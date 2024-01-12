package com.example.parking.application;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.UpperSnakeCaseStrategy.class)
public class ApiResponse {

    private String parkingCode;
    private String parkingName;
    private String parkingTypeNM;
    private String operationRule;
    private String tel;

    private String addr;
    private String lat;
    private String lng;

    private String queStatus;
    private String capacity;
    private String curParking;

    private String payNM;
    private String saturdayPayNM;
    private String holidayPayNM;
    private String rates;
    private String timeRate;
    private String addRates;
    private String addTimeRate;
    private String dayMaximum;

    private String weekdayBeginTime;
    private String weekdayEndTime;
    private String weekendBeginTime;
    private String weekendEndTime;
    private String holidayBeginTime;
    private String holidayEndTime;
}
