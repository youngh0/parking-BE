package com.example.parking.auth.authcode.application.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthCodeCertificateRequest {

    private String destination;
    private String authType;
    private String authCode;

    public AuthCodeCertificateRequest(String destination, String authType, String authCode) {
        this.destination = destination;
        this.authType = authType;
        this.authCode = authCode;
    }
}
