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

    public static List<PayType> collectMatch(List<String> descriptions) {
        return descriptions.stream()
                .filter(PayType::contains)
                .map(PayType::find)
                .toList();
    }

    private static boolean contains(String description) {
        return Arrays.stream(values())
                .anyMatch(e -> description.startsWith(e.getDescription()));
    }

    public static PayType find(String description) {
        return Arrays.stream(values())
                .filter(e -> description.startsWith(e.getDescription()))
                .findAny()
                .orElse(NO_INFO);
    }

    @Override
    public PayType getDefault() {
        return NO_INFO;
    }
}

