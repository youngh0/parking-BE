package com.example.parking.auth.authcode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AuthCodeRepositoryTest {

    @Autowired
    private AuthCodeRepository authCodeRepository;

    @Test
    void 인증코드가_여러개인_경우_가장_최근에_생성된_인증코드를_반환한다() {
        String destination = "destination";

        String authCode = "000000";
        AuthCode savedAuthCode = authCodeRepository.save(
                new AuthCode(destination, authCode, AuthCodePlatform.MAIL));

        AuthCode findAuthCode = authCodeRepository.findUsableAuthCode(destination, authCode,
                AuthCodePlatform.MAIL).get();

        assertAll(
                () -> assertThat(findAuthCode.getId()).isEqualTo(savedAuthCode.getId()),
                () -> assertThat(findAuthCode.getAuthCode()).isEqualTo(savedAuthCode.getAuthCode())
        );
    }
}
