package com.example.parking.domain.parking;

import com.example.parking.domain.searchcondition.SearchConditionAvailable;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
public enum OperationType implements SearchConditionAvailable {

    PUBLIC("공영"),
    PRIVATE("민영"),
    NO_INFO("정보 없음");

    private final String description;

    OperationType(String description) {
        this.description = description;
    }

    public static List<String> getAllValues() {
        return Arrays.stream(OperationType.values())
                .filter(operationType -> operationType != OperationType.NO_INFO)
                .map(operationType -> operationType.description)
                .toList();
    }

    public static List<OperationType> collectMatch(List<String> descriptions) {
        return descriptions.stream()
                .filter(OperationType::contains)
                .map(OperationType::find)
                .toList();
    }

    private static boolean contains(String description) {
        return Arrays.stream(values())
                .anyMatch(e -> description.startsWith(e.getDescription()));
    }

    public static OperationType find(String description) {
        return Arrays.stream(values())
                .filter(e -> description.startsWith(e.getDescription()))
                .findAny()
                .orElse(NO_INFO);
    }

    @Override
    public OperationType getDefault() {
        return NO_INFO;
    }
}
