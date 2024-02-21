package com.example.parking.auth.authcode;

import org.springframework.data.repository.Repository;

public interface AuthCodeRepository extends Repository<AuthCode, Long> {

    public AuthCode save(AuthCode authCode);
}
