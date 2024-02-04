package com.example.parking.application.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberSignupRequest {

    private String name;
    private String email;
    private String password;
    private String nickname;
}
