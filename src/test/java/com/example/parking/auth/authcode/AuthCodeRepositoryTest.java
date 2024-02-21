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
        // given
        String destination = "destination";
        String oldAuthCode = "000000";
        String newAuthCode = "123456";

        // when
        AuthCode savedOldAuthCode = authCodeRepository.save(
                new AuthCode(destination, oldAuthCode, AuthCodePlatform.MAIL, AuthCodeCategory.SIGN_UP));
        AuthCode savedNewAuthCode = authCodeRepository.save(
                new AuthCode(destination, newAuthCode, AuthCodePlatform.MAIL, AuthCodeCategory.SIGN_UP));

        AuthCode findAuthCode = authCodeRepository.findRecentlyAuthCodeBy(destination, AuthCodePlatform.MAIL,
                AuthCodeCategory.SIGN_UP).get();

        // then
        assertAll(
                () -> assertThat(findAuthCode.getId()).isEqualTo(savedNewAuthCode.getId()),
                () -> assertThat(findAuthCode.getAuthCode()).isEqualTo(savedNewAuthCode.getAuthCode())
        );
    }
}
