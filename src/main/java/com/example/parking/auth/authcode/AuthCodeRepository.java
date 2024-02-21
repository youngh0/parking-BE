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
                     and ac.authCodePlatform = :authCodePlatform 
                     and ac.isUsed = false 
                order by ac.createdAt desc  
                limit 1 
            """)
    Optional<AuthCode> findUsableAuthCode(@Param("destination") String destination,
                                          @Param("authCode") String authCode,
                                          @Param("authCodePlatform") AuthCodePlatform authCodePlatform);
}
