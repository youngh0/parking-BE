package com.example.parking.auth;

import java.util.Arrays;

public enum AuthCodeType {

    MAIL("mail");

    private final String type;

    AuthCodeType(String type) {
        this.type = type;
    }

    public static AuthCodeType find(String authType) {
        return Arrays.stream(AuthCodeType.values())
                .filter(authCodeType -> authCodeType.type.equals(authType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 인증 형식"));
    }
}
