package com.example.parking.domain.parking;

import static lombok.AccessLevel.*;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class Fee {

    private Integer fee;

    private Fee(Integer fee) {
        this.fee = fee;
    }

    public static Fee from(Integer fee) {
        return new Fee(fee);
    }
}
