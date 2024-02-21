package com.example.parking.application.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthCodeRequest {

    private String destination;
    private String authType;

    public AuthCodeRequest(String destination, String authType) {
        this.destination = destination;
        this.authType = authType;
    }
}
