package com.example.parking.auth.authcode;

import java.util.Arrays;

public enum AuthCodePlatform {

    MAIL("mail");

    private final String platform;

    AuthCodePlatform(String platform) {
        this.platform = platform;
    }

    public static AuthCodePlatform find(String authType) {
        return Arrays.stream(AuthCodePlatform.values())
                .filter(authCodeType -> authCodeType.platform.equals(authType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 인증 형식"));
    }

    public String getPlatform() {
        return platform;
    }
}
