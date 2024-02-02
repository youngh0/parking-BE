package com.example.parking.domain.parking;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class FreePolicyTest {

    @ParameterizedTest
    @MethodSource("getDay")
    void 요금이_존재하는_날인지를_확인한다(
            boolean isFree,
            boolean isSaturdayFree,
            boolean isHolidayFree,
            Day today,
            boolean expected
    ) {
        // given
        FreePolicy freePolicy = new FreePolicy(isFree, isSaturdayFree, isHolidayFree);
        DayParking dayParking = new DayParking(100, today);

        // when
        boolean actual = freePolicy.isNotFreeDay(dayParking);

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> getDay() {
        return Stream.of(
                Arguments.of(true, true, true, Day.WEEKDAY, false),
                Arguments.of(false, true, true, Day.WEEKDAY, true),
                Arguments.of(false, true, false, Day.WEEKDAY, true),
                Arguments.of(false, false, true, Day.WEEKDAY, true),
                Arguments.of(false, false, false, Day.WEEKDAY, true),

                Arguments.of(true, true, true, Day.SATURDAY, false),
                Arguments.of(false, true, true, Day.SATURDAY, false),
                Arguments.of(false, true, false, Day.SATURDAY, false),
                Arguments.of(false, false, true, Day.SATURDAY, true),
                Arguments.of(false, false, false, Day.SATURDAY, true),

                Arguments.of(true, true, true, Day.HOLIDAY, false),
                Arguments.of(false, true, true, Day.HOLIDAY, false),
                Arguments.of(false, true, false, Day.HOLIDAY, true),
                Arguments.of(false, false, true, Day.HOLIDAY, false),
                Arguments.of(false, false, false, Day.HOLIDAY, true)
        );
    }
}
