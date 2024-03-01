package com.example.parking.auth.authcode.application;


import com.example.parking.auth.authcode.event.AuthCodeCreateEvent;
import com.example.parking.auth.authcode.infrastructure.AuthCodeSender;
import java.time.Instant;
import java.util.StringJoiner;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class AuthCodeEventListener {

    @Value("${authcode.expired-time}")
    private Integer authCodeExpired;

    private final AuthCodeSender authCodeSender;
    private final TaskScheduler taskScheduler;
    private final RedisTemplate<String, String> redisTemplate;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendAuthCode(AuthCodeCreateEvent authCodeCreateEvent) {
        String destination = authCodeCreateEvent.getDestination();
        String authCode = authCodeCreateEvent.getAuthCode();
        authCodeSender.send(destination, authCode);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void scheduledAuthCodeRemove(AuthCodeCreateEvent authCodeCreateEvent) {
        String destination = authCodeCreateEvent.getDestination();
        String authCodePlatform = authCodeCreateEvent.getAuthCodePlatform();
        String authCodeCategory = authCodeCreateEvent.getAuthCodeCategory();

        String authCodeKey = generateAuthCodeKey(destination, authCodePlatform, authCodeCategory);
        taskScheduler.schedule(() -> redisTemplate.delete(authCodeKey), Instant.now().plusSeconds(authCodeExpired));
    }

    private String generateAuthCodeKey(String destination, String authCodePlatform,
                                       String authCodeCategory) {
        StringJoiner stringJoiner = new StringJoiner(":");
        return stringJoiner
                .add(destination)
                .add(authCodePlatform)
                .add(authCodeCategory)
                .toString();
    }
}
