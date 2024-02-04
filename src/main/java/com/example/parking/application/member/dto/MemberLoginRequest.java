package com.example.parking.application.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLoginRequest {

    private String email;
    private String password;
}
