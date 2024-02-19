package com.example.parking.application.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberInfoResult {

    private String name;
    private String email;

    public MemberInfoResult(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
