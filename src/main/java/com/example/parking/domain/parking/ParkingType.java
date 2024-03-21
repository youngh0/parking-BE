package com.example.parking.domain.parking;

import com.example.parking.domain.searchcondition.SearchConditionAvailable;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
public enum ParkingType implements SearchConditionAvailable {
    OFF_STREET("노외"),
    ON_STREET("노상"),
    MECHANICAL("기계"),
    NO_INFO("정보 없음");

    private final String description;

    ParkingType(String description) {
        this.description = description;
    }

    public static List<String> getAllValues() {
        return Arrays.stream(ParkingType.values())
                .filter(parkingType -> parkingType != ParkingType.NO_INFO)
                .map(parkingType -> parkingType.description)
                .toList();
    }

    public static List<ParkingType> collectMatch(List<String> descriptions) {
        return descriptions.stream()
                .filter(ParkingType::contains)
                .map(ParkingType::find)
                .toList();
    }

    private static boolean contains(String description) {
        return Arrays.stream(values())
                .anyMatch(e -> description.startsWith(e.getDescription()));
    }

    @Override
    public ParkingType getDefault() {
        return NO_INFO;
    }

    public static ParkingType find(String description) {
        return Arrays.stream(values())
                .filter(e -> description.startsWith(e.getDescription()))
                .findAny()
                .orElse(NO_INFO);
    }

    public String getDescription() {
        return description;
    }
}
