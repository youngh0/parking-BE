package com.example.parking.application.coordinate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CoordinateResponse {

    @JsonProperty("documents")
    private List<ExactLocation> exactLocations;

    @JsonProperty("meta")
    private Meta meta;

    public CoordinateResponse(List<ExactLocation> exactLocations, Meta meta) {
        this.exactLocations = exactLocations;
        this.meta = meta;
    }

    @Getter
    @NoArgsConstructor
    public static class ExactLocation {
        private Double x;
        private Double y;
    }

    @Getter
    @NoArgsConstructor
    public static class Meta {

        @JsonProperty("total_count")
        private Integer totalCount;

        public Meta(Integer totalCount) {
            this.totalCount = totalCount;
        }
    }
}
