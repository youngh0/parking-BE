package com.example.parking.domain.searchcondition;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
public enum FeeType implements SearchConditionAvailable {

    FREE("무료"), PAID("유료"), NO_INFO("정보 없음");

    private final String description;

    FeeType(String description) {
        this.description = description;
    }

    public static List<String> getAllValues() {
        return Arrays.stream(FeeType.values())
                .filter(feeType -> feeType != FeeType.NO_INFO)
                .map(feeType -> feeType.description)
                .toList();
    }

    @Override
    public FeeType getDefault() {
        return NO_INFO;
    }
}
