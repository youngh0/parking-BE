package com.example.parking.auth.authcode;

import java.util.Arrays;

public enum AuthCodeCategory {

    SIGN_UP("signUp"),
    FIND_PASSWORD("findPassword");

    private final String categoryName;

    AuthCodeCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public static AuthCodeCategory find(String authCodeCategory) {
        return Arrays.stream(AuthCodeCategory.values())
                .filter(category -> category.categoryName.equals(authCodeCategory))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 인증 코드 종류"));
    }
}
