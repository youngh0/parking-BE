package com.example.parking.auth;

import com.example.parking.auth.authcode.AuthCodeCategory;
import com.example.parking.auth.authcode.AuthCodePlatform;
import com.example.parking.auth.authcode.application.AuthCodeValidator;
import com.example.parking.auth.authcode.application.dto.AuthCodeCertificateRequest;
import com.example.parking.auth.authcode.application.dto.AuthCodeRequest;
import com.example.parking.auth.authcode.event.AuthCodeCreateEvent;
import com.example.parking.auth.authcode.util.AuthCodeKeyConverter;
import com.example.parking.auth.session.MemberSession;
import com.example.parking.auth.session.MemberSessionRepository;
import com.example.parking.support.exception.ClientException;
import com.example.parking.support.exception.ExceptionInformation;
import com.example.parking.util.authcode.AuthCodeGenerator;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private static final Long DURATION_MINUTE = 30L;

    private final MemberSessionRepository memberSessionRepository;
    private final AuthCodeGenerator authCodeGenerator;
    private final AuthCodeValidator authCodeValidator;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public String createSession(Long memberId) {
        LocalDateTime current = LocalDateTime.now();
        String uuid = UUID.randomUUID().toString();

        MemberSession memberSession = new MemberSession(uuid, memberId, current, current.plusMinutes(DURATION_MINUTE));
        memberSessionRepository.save(memberSession);
        return memberSession.getSessionId();
    }

    @Transactional(readOnly = true)
    public MemberSession findSession(String sessionId) {
        return memberSessionRepository.findBySessionIdAndExpiredAtIsGreaterThanEqual(sessionId,
                        LocalDateTime.now())
                .orElseThrow(() -> new ClientException(ExceptionInformation.UNAUTHORIZED));
    }

    @Transactional
    public void updateSessionExpiredAt(MemberSession session) {
        session.updateExpiredAt(LocalDateTime.now().plusMinutes(DURATION_MINUTE));
        memberSessionRepository.save(session);
    }

    @Transactional
    public String createAuthCode(AuthCodeRequest authCodeRequest) {
        String destination = authCodeRequest.getDestination();
        AuthCodePlatform authCodePlatform = AuthCodePlatform.find(authCodeRequest.getAuthPlatform());
        AuthCodeCategory authCodeCategory = AuthCodeCategory.find(authCodeRequest.getAuthCodeCategory());

        authCodeValidator.validate(authCodePlatform, destination);
        String randomAuthCode = authCodeGenerator.generateAuthCode();
        String authCodeKey = AuthCodeKeyConverter.convert(randomAuthCode, destination, authCodePlatform.getPlatform(),
                authCodeCategory.getCategoryName());
        redisTemplate.opsForValue().set(authCodeKey, randomAuthCode, 300L, TimeUnit.SECONDS);

        publishAuthCodeCreateEvent(destination, authCodePlatform, authCodeCategory, randomAuthCode);
        return randomAuthCode;
    }

    private void publishAuthCodeCreateEvent(String destination, AuthCodePlatform authCodePlatform,
                                            AuthCodeCategory authCodeCategory, String randomAuthCode) {
        applicationEventPublisher.publishEvent(
                new AuthCodeCreateEvent(
                        destination,
                        randomAuthCode,
                        authCodePlatform.getPlatform(),
                        authCodeCategory.getCategoryName()
                )
        );
    }

    @Transactional
    public void certificateAuthCode(AuthCodeCertificateRequest authCodeCertificateRequest) {
        String authCode = authCodeCertificateRequest.getAuthCode();
        String destination = authCodeCertificateRequest.getDestination();
        AuthCodePlatform authCodePlatform = AuthCodePlatform.find(authCodeCertificateRequest.getAuthCodePlatform());
        AuthCodeCategory authCodeCategory = AuthCodeCategory.find(authCodeCertificateRequest.getAuthCodeCategory());

        String authCodeKey = AuthCodeKeyConverter.convert(authCode, destination, authCodePlatform.getPlatform(),
                authCodeCategory.getCategoryName());
        String findResult = redisTemplate.opsForValue().getAndDelete(authCodeKey);
        if (findResult == null) {
            throw new ClientException(ExceptionInformation.INVALID_AUTH_CODE);
        }
    }
}
