package com.example.parking.domain.member;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class MemberId {

    private Long memberId;

    public MemberId(Long memberId) {
        this.memberId = memberId;
    }
}
