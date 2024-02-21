package com.example.parking.auth;

import com.example.parking.auth.authcode.AuthCode;
import com.example.parking.auth.authcode.AuthCodeRepository;
import com.example.parking.auth.authcode.AuthCodeType;
import com.example.parking.auth.authcode.application.dto.AuthCodeCertificateRequest;
import com.example.parking.auth.authcode.application.dto.AuthCodeRequest;
import com.example.parking.auth.authcode.event.AuthCodeSendEvent;
import com.example.parking.auth.session.MemberSession;
import com.example.parking.auth.session.MemberSessionRepository;
import com.example.parking.util.authcode.AuthCodeGenerator;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private static final Long DURATION_MINUTE = 30L;

    private final MemberSessionRepository memberSessionRepository;
    private final AuthCodeRepository authCodeRepository;
    private final AuthCodeGenerator authCodeGenerator;
    private final ApplicationEventPublisher applicationEventPublisher;

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
                .orElseThrow(() -> new UnAuthorizationException("존재하지 않는 sessionId 입니다.", sessionId));
    }

    @Transactional
    public void updateSessionExpiredAt(MemberSession session) {
        session.updateExpiredAt(LocalDateTime.now().plusMinutes(DURATION_MINUTE));
        memberSessionRepository.save(session);
    }

    @Transactional
    public void createAuthCode(AuthCodeRequest authCodeRequest) {
        String destination = authCodeRequest.getDestination();
        String authType = authCodeRequest.getAuthType();
        AuthCodeType authCodeType = AuthCodeType.find(authType);

        String randomAuthCode = authCodeGenerator.generateAuthCode();
        AuthCode authCode = new AuthCode(destination, randomAuthCode, authCodeType);
        authCodeRepository.save(authCode);
        applicationEventPublisher.publishEvent(new AuthCodeSendEvent(destination, randomAuthCode));
    }

    @Transactional
    public void certificateAuthCode(AuthCodeCertificateRequest authCodeCertificateRequest) {
        String destination = authCodeCertificateRequest.getDestination();
        AuthCodeType authCodeType = AuthCodeType.find(authCodeCertificateRequest.getAuthType());
        String requestAuthCode = authCodeCertificateRequest.getAuthCode();

        AuthCode authCode = authCodeRepository.findUsableAuthCode(destination, requestAuthCode, authCodeType)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 인증 요청"));
        authCode.certificate();
    }
}
