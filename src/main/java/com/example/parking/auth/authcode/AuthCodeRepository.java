package com.example.parking.auth.authcode;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface AuthCodeRepository extends Repository<AuthCode, Long> {

    AuthCode save(AuthCode authCode);

    @Query(value = """
                select ac 
                from AuthCode ac 
                where ac.destination = :destination 
                     and ac.authCode = :authCode 
                     and ac.authCodeType = :authCodeType 
                     and ac.isUsed = false 
            """)
    Optional<AuthCode> existsUsableAuthCode(@Param("destination") String destination,
                                            @Param("authCode") String authCode,
                                            @Param("authCodeType") AuthCodeType authCodeType);
}
