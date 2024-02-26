package com.example.parking.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void member_삭제_시_soft_delete() {
        Member member = new Member("name", "email", "nickname", new Password("password"));
        Member savedMember = memberRepository.save(member);

        memberRepository.deleteById(savedMember.getId());
        Member findMember = memberRepository.findById(savedMember.getId()).get();

        assertThat(findMember.getDeleted()).isTrue();
    }
}
