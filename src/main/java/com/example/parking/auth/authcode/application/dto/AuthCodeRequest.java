package com.example.parking.auth.authcode.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthCodeRequest {

    private String destination;
    private String authType;
    private String authCodeCategory;

    public AuthCodeRequest(String destination, String authType, String authCodeCategory) {
        this.destination = destination;
        this.authType = authType;
        this.authCodeCategory = authCodeCategory;
    }
}
