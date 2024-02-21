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
                     and ac.authCodePlatform = :authCodePlatform 
                     and ac.authCodeCategory = :authCodeCategory 
                order by ac.createdAt desc  
                limit 1 
            """)
    Optional<AuthCode> findRecentlyAuthCodeBy(@Param("destination") String destination,
                                              @Param("authCodePlatform") AuthCodePlatform authCodePlatform,
                                              @Param("authCodeCategory") AuthCodeCategory authCodeCategory
    );
}
