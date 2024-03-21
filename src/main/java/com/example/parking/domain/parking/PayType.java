package com.example.parking.domain.parking;

import com.example.parking.domain.searchcondition.SearchConditionAvailable;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
public enum PayType implements SearchConditionAvailable {

    CASH("현금"),
    CARD("카드"),
    BANK_TRANSFER("계좌"),
    NO_INFO("정보 없음");

    private final String description;

    PayType(String description) {
        this.description = description;
    }

    public static List<String> getAllValues() {
        return Arrays.stream(PayType.values())
                .filter(payType -> payType != PayType.NO_INFO)
                .map(payType -> payType.description)
                .toList();
    }

    @Override
    public PayType getDefault() {
        return NO_INFO;
    }
}

