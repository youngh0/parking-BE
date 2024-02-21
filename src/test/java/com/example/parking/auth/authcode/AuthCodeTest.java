package com.example.parking.auth.authcode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class AuthCodeTest {

    @Test
    void 사용하지_않은_인증코드를_사용한다() {
        // given
        String authCodeString = "000000";
        AuthCode authCode = new AuthCode("destination", authCodeString, AuthCodePlatform.MAIL,
                AuthCodeCategory.SIGN_UP);

        // when
        authCode.certificate(authCodeString);

        // then
        assertThat(authCode.getIsUsed()).isTrue();
    }

    @Test
    void 사용한_인증코드를_다시_사용하면_예외발생() {
        // given
        String authCodeString = "000000";
        AuthCode authCode = new AuthCode("destination", authCodeString, AuthCodePlatform.MAIL,
                AuthCodeCategory.SIGN_UP);

        // when
        authCode.certificate(authCodeString);

        assertThatThrownBy(() -> authCode.certificate(authCodeString))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 인증코드가_다르면_예외발생() {
        // given
        String authCodeString = "000000";
        AuthCode authCode = new AuthCode("destination", authCodeString, AuthCodePlatform.MAIL,
                AuthCodeCategory.SIGN_UP);

        // when
        String wrongAuthCode = "111111";
        assertThatThrownBy(() -> authCode.certificate(wrongAuthCode))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
