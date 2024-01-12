package com.example.parking.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingInfo {

    private int list_total_count;

    @JsonProperty("RESULT")
    private Result result;

    private List<ApiResponse> row;
}
