package com.example.parking.domain.searchcondition;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum FeeType implements SearchConditionAvailable {

    FREE("무료"), PAID("유료"), NO_INFO("정보 없음");

    private final String description;

    FeeType(String description) {
        this.description = description;
    }

    public static FeeType find(String description) {
        return Arrays.stream(values())
                .filter(e -> description.startsWith(e.getDescription()))
                .findAny()
                .orElse(NO_INFO);
    }

    @Override
    public FeeType getDefault() {
        return NO_INFO;
    }
}
