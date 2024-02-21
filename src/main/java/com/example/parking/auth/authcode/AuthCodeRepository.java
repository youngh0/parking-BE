package com.example.parking.auth.authcode;

import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface AuthCodeRepository extends Repository<AuthCode, Long> {

    AuthCode save(AuthCode authCode);

    Optional<AuthCode> findByAuthCodeAndDestinationAndAuthCodeTypeAndIsUsedIsFalse(String authCode, String destination,
                                                                                   AuthCodeType authCodeType);
}
