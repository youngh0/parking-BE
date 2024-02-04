package com.example.parking.api.member;

import com.example.parking.application.member.MemberService;
import com.example.parking.application.member.dto.MemberLoginRequest;
import com.example.parking.application.member.dto.MemberSignupRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/users")
    public ResponseEntity<Void> signup(@RequestBody MemberSignupRequest request) {
        memberService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("login")
    public ResponseEntity<Void> login(HttpServletRequest httpServletRequest, @RequestBody MemberLoginRequest request) {
        HttpSession session = httpServletRequest.getSession();
        Long memberId = memberService.login(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
