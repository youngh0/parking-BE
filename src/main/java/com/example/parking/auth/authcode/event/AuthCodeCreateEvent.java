package com.example.parking.auth.authcode.event;

public class AuthCodeCreateEvent {

    private final String destination;
    private final String authCode;

    public AuthCodeCreateEvent(String destination, String authCode) {
        this.destination = destination;
        this.authCode = authCode;
    }

    public String getDestination() {
        return destination;
    }

    public String getAuthCode() {
        return authCode;
    }
}
