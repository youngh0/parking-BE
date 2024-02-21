package com.example.parking.auth.authcode;

import com.example.parking.domain.AuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AuthCode extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destination;

    private String authCode;

    @Enumerated(EnumType.STRING)
    private AuthCodeType authCodeType;

    private Boolean isUsed = Boolean.FALSE;

    public AuthCode(String destination, String authCode, AuthCodeType authCodeType) {
        this.destination = destination;
        this.authCode = authCode;
        this.authCodeType = authCodeType;
    }
}
