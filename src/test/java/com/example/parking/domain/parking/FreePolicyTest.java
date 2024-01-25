package com.example.parking.domain.parking;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class FreePolicyTest {

    @Test
    void 언제나_무료이면_계산_시간이_0이다() {
        // given
        FreePolicy freePolicy = new FreePolicy(true, true, true);
        int weekdayMinutes = 100;
        int saturdayMinutes = 200;
        int weekendMinutes = 300;
        int expected = 0;

        // when
        int actual = freePolicy.calculateMinutes(weekdayMinutes, saturdayMinutes, weekendMinutes);

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 토요일과_공휴일이_무료이면_주중_시간만_나온다() {
        // given
        FreePolicy freePolicy = new FreePolicy(false, true, true);
        int weekdayMinutes = 100;
        int saturdayMinutes = 200;
        int weekendMinutes = 300;
        int expected = 100;

        // when
        int actual = freePolicy.calculateMinutes(weekdayMinutes, saturdayMinutes, weekendMinutes);

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 토요일만_무료이면_주중_시간에_공휴일_시간을_더한_시간이_나온다() {
        // given
        FreePolicy freePolicy = new FreePolicy(false, true, false);
        int weekdayMinutes = 100;
        int saturdayMinutes = 200;
        int weekendMinutes = 300;
        int expected = 400;

        // when
        int actual = freePolicy.calculateMinutes(weekdayMinutes, saturdayMinutes, weekendMinutes);

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 공휴일만_무료이면_주중_시간에_토요일_시간을_더한_시간이_나온다() {
        // given
        FreePolicy freePolicy = new FreePolicy(false, false, true);
        int weekdayMinutes = 100;
        int saturdayMinutes = 200;
        int weekendMinutes = 300;
        int expected = 300;

        // when
        int actual = freePolicy.calculateMinutes(weekdayMinutes, saturdayMinutes, weekendMinutes);

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 언제나_유료이면_주중_시간에_토요일_시간에_공휴일_시간을_더한_시간이_나온다() {
        // given
        FreePolicy freePolicy = new FreePolicy(false, false, false);
        int weekdayMinutes = 100;
        int saturdayMinutes = 200;
        int weekendMinutes = 300;
        int expected = 600;

        // when
        int actual = freePolicy.calculateMinutes(weekdayMinutes, saturdayMinutes, weekendMinutes);

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
