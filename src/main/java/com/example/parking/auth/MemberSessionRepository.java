package com.example.parking.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberSessionRepository extends JpaRepository<MemberSession, String> {
}
