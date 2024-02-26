package com.example.parking.application.member;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.parking.application.member.dto.MemberSignupRequest;
import com.example.parking.domain.member.Member;
import com.example.parking.domain.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 회원탈퇴() {
        Long memberId = memberService.signup(new MemberSignupRequest("name", "email", "nickname", "password"));
        memberService.deleteMember(memberId);

        Member member = memberRepository.getById(memberId);
        assertThat(member.getDeleted()).isTrue();
    }

}
