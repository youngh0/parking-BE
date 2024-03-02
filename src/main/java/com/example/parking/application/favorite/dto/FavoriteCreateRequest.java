package com.example.parking.application.favorite.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavoriteCreateRequest {

    private Long memberId;
    private Long parkingId;

    public FavoriteCreateRequest(Long memberId, Long parkingId) {
        this.memberId = memberId;
        this.parkingId = parkingId;
    }
}
