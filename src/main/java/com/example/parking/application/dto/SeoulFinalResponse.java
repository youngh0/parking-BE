package com.example.parking.application.dto;

import com.example.parking.application.seoul.dto.SeoulCityParkingResponse.ParkingInfo.SeoulCityParking;
import java.util.List;
import lombok.Getter;

@Getter
public class SeoulFinalResponse {

    private final String region;
    private final List<SeoulCityParking> seoulCityParking;

    public SeoulFinalResponse(String region, List<SeoulCityParking> seoulCityParking) {
        this.region = region;
        this.seoulCityParking = seoulCityParking;
    }
}
