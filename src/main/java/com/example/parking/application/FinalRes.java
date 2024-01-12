package com.example.parking.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FinalRes {

    @JsonProperty("GetParkingInfo")
    private ParkingInfo GetParkingInfo;
}
