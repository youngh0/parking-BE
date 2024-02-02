package com.example.parking.domain.parking;

import lombok.Getter;

@Getter
public enum PayType {

    CASH("현금"),
    CARD("카드"),
    BANK_TRANSFER("계좌 이체"),
    NO_INFO("정보 없음");

    private final String description;

    PayType(final String description) {
        this.description = description;
    }
}

