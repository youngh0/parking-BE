package com.example.parking.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CoordinateResponse {

    @JsonProperty("documents")
    private List<Document> documents;

    @JsonProperty("meta")
    private Meta meta;

    public CoordinateResponse(List<Document> documents, Meta meta) {
        this.documents = documents;
        this.meta = meta;
    }

    @Getter
    @NoArgsConstructor
    public static class Document {
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
