package com.example.parking.application.favorite.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavoriteDeleteRequest {

    private Long memberId;
    private Long parkingId;

    public FavoriteDeleteRequest(Long memberId, Long parkingId) {
        this.memberId = memberId;
        this.parkingId = parkingId;
    }
}
