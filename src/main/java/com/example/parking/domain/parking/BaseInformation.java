package com.example.parking.domain.parking;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class BaseInformation {

    private String name;
    private String tel;
    private String address;

    public BaseInformation(String name, String tel, String address) {
        this.name = name;
        this.tel = tel;
        this.address = address;
    }
}
