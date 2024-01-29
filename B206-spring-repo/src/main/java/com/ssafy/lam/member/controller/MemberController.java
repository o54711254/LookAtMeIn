package com.ssafy.lam.member.controller;

import com.ssafy.lam.dto.MemberLoginRequestDto;
import com.ssafy.lam.dto.TokenInfo;
import com.ssafy.lam.member.model.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Service
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<TokenInfo> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        String memberId = memberLoginRequestDto.getMemberId();
        String password = memberLoginRequestDto.getPassword();
        System.out.println("password = " + password);
        TokenInfo tokenInfo = memberService.login(memberId, password);
        return ResponseEntity.ok(tokenInfo);

    }
}
