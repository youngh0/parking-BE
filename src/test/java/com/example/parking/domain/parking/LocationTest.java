package com.example.parking.domain.parking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

class LocationTest {

    @Test
    void 위도와_경도를_통해_Point객체를_가진_Location객체를_생성한다() {
        double latitude = 37.4809626;
        double longitude = 127.1216765;

        Location location = Location.of(latitude, longitude);

        assertAll(
                () -> assertThat(location.getLongitude()).isEqualTo(longitude),
                () -> assertThat(location.getLatitude()).isEqualTo(latitude)
        );
    }
}
