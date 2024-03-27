package com.example.parking.application.parking.dto;

import com.example.parking.application.review.dto.ReviewInfoResponse;
import lombok.Getter;

public class ParkingInfoResponse {

    private String parkingName;
    private String parkingType;
    private Double latitude;
    private Double longitude;
    private FeeInfo feeInfo;
    private Integer currentParkingCount;
    private Integer capacity;
    private Integer lastUpdated;
    private String tel;
    private String paymentType;
    private ReviewInfoResponse reviewInfo;

    public ParkingInfoResponse(String parkingName, String parkingType, Double latitude, Double longitude,
                               FeeInfo feeInfo,
                               Integer currentParkingCount, Integer capacity, Integer lastUpdated, String tel,
                               String paymentType, ReviewInfoResponse reviewInfo) {
        this.parkingName = parkingName;
        this.parkingType = parkingType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.feeInfo = feeInfo;
        this.currentParkingCount = currentParkingCount;
        this.capacity = capacity;
        this.lastUpdated = lastUpdated;
        this.tel = tel;
        this.paymentType = paymentType;
        this.reviewInfo = reviewInfo;
    }

    @Getter
    public static class FeeInfo {
        private Integer fee;
        private Integer time;

        public FeeInfo(Integer fee, Integer time) {
            this.fee = fee;
            this.time = time;
        }
    }
}
