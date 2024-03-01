package com.example.parking.auth.authcode.application;


import com.example.parking.auth.authcode.event.AuthCodeCreateEvent;
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
    public void sendAuthCode(AuthCodeCreateEvent authCodeCreateEvent) {
        String destination = authCodeCreateEvent.getDestination();
        String authCode = authCodeCreateEvent.getAuthCode();
        authCodeSender.send(destination, authCode);
    }
}
