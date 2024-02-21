package com.example.parking.auth.authcode.application;


import com.example.parking.auth.authcode.event.AuthCodeSendEvent;
import com.example.parking.auth.authcode.infrastructure.AuthCodeSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class AuthCodeEventListener {

    private final AuthCodeSender authCodeSender;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendAuthCode(AuthCodeSendEvent authCodeSendEvent) {
        String destination = authCodeSendEvent.getDestination();
        String authCode = authCodeSendEvent.getAuthCode();
        authCodeSender.send(destination, authCode);
    }
}
