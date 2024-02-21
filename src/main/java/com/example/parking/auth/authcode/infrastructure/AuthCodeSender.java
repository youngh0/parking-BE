package com.example.parking.auth.authcode.infrastructure;

public interface AuthCodeSender {

    public void send(String destination, String authCode);
}
