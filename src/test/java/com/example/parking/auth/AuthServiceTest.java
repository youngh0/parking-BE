package com.example.parking.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.parking.auth.authcode.AuthCode;
import com.example.parking.auth.authcode.AuthCodeCategory;
import com.example.parking.auth.authcode.AuthCodePlatform;
import com.example.parking.auth.authcode.AuthCodeRepository;
import com.example.parking.auth.authcode.application.dto.AuthCodeRequest;
import com.example.parking.auth.session.MemberSession;
import com.example.parking.auth.session.MemberSessionRepository;
import com.example.parking.util.authcode.AuthCodeGenerator;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class AuthServiceTest {

    private static final String AUTH_CODE = "111111";

    private AuthService authService;
    private AuthCodeRepository authCodeRepository;

    @Autowired
    public AuthServiceTest(MemberSessionRepository memberSessionRepository, AuthCodeRepository authCodeRepository,
                           ApplicationEventPublisher applicationEventPublisher) {
        AuthCodeGenerator fixAuthCodeGenerator = () -> AUTH_CODE;
        this.authCodeRepository = authCodeRepository;
        this.authService = new AuthService(memberSessionRepository, authCodeRepository, fixAuthCodeGenerator,
                applicationEventPublisher);
    }

    @Test
    void 세션_아이디에_해당하는_세션을_찾는다() {
        // given
        Long memberId = 1L;
        String sessionId = authService.createSession(memberId);

        // when, then
        assertThatNoException()
                .isThrownBy(() -> authService.findSession(sessionId));
    }

    @Test
    void 세션_아이디에_해당하는_세션이_존재하지_않으면_예외를_던진다() {
        // given
        String wrongSessionId = "아무세션아이디";

        // when, then
        assertThatThrownBy(() -> authService.findSession(wrongSessionId))
                .isInstanceOf(UnAuthorizationException.class);
    }

    @Test
    void 세션_만료시간을_갱신한다() {
        // given
        Long memberId = 1L;
        String sessionId = authService.createSession(memberId);
        MemberSession originSession = authService.findSession(sessionId);
        LocalDateTime originExpiredAt = originSession.getExpiredAt();

        // when
        authService.updateSessionExpiredAt(originSession);
        MemberSession updatedSession = authService.findSession(sessionId);
        LocalDateTime updatedExpiredAt = updatedSession.getExpiredAt();

        // then
        assertThat(updatedExpiredAt).isAfter(originExpiredAt);
    }

    @Test
    void 인증_코드를_저장한다() {
        // given
        String authCodeDestination = "destination";
        String authCodePlatform = "mail";
        String authCodeCategory = "signUp";

        // when
        authService.createAuthCode(new AuthCodeRequest(authCodeDestination, authCodePlatform, authCodeCategory));
        Optional<AuthCode> result = authCodeRepository.findRecentlyAuthCodeBy(
                authCodeDestination, AuthCodePlatform.find(authCodePlatform), AuthCodeCategory.find(authCodeCategory));

        // then
        assertThat(result).isNotEmpty();
    }
}
