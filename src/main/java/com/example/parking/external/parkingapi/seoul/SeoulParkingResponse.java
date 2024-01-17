package com.example.parking.external.parkingapi.seoul;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 서울시 시영주차장 실시간 주차정보
 */
@Getter
@NoArgsConstructor
public class SeoulParkingResponse {

    @JsonProperty("GetParkingInfo")
    private ParkingInfo parkingInfo;

    @Getter
    @NoArgsConstructor
    public static class ParkingInfo {

        @JsonProperty("list_total_count")
        private int parkingCount;

        @JsonProperty("RESULT")
        private Result result;

        @JsonProperty("row")
        private List<SeoulParking> rows;

        @NoArgsConstructor
        @Getter
        public static class Result {

            @JsonProperty("CODE")
            private String code;

            @JsonProperty("MESSAGE")
            private String message;
        }

        @Getter
        @NoArgsConstructor
        @JsonNaming(value = PropertyNamingStrategies.UpperSnakeCaseStrategy.class)
        public static class SeoulParking {

            private String parkingCode;
            private String parkingName;
            private String parkingTypeNM;
            private String operationRule;
            private String tel;

            private String addr;
            private String lat;
            private String lng;

            private Integer queStatus;
            private Integer capacity;
            private Integer curParking;

            private String payNM;
            private String saturdayPayNM;
            private String holidayPayNM;
            private Integer rates;
            private Integer timeRate;
            private Integer addRates;
            private Integer addTimeRate;
            private Integer dayMaximum;

            private String weekdayBeginTime;
            private String weekdayEndTime;
            private String weekendBeginTime;
            private String weekendEndTime;
            private String holidayBeginTime;
            private String holidayEndTime;

            public void setCapacity(int capacity) {
                this.capacity = capacity;
            }

            public void plusCapacity(int capacity) {
                this.capacity += capacity;
            }
        }
    }
}
