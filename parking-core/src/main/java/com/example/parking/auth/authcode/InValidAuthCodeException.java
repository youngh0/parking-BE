package com.example.parking.auth.authcode;

public class InValidAuthCodeException extends RuntimeException {

    private final String message;

    public InValidAuthCodeException(String message) {
        super(message);
        this.message = message;
    }
}
