package com.example.parking.domain.parking;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class BaseInformation {

    private String tel;
    private String address;
    private String name;
    private String code;

    public BaseInformation(String tel, String address, String name, String code) {
        this.tel = tel;
        this.address = address;
        this.name = name;
        this.code = code;
    }
}
