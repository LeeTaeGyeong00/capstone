package com.example.bangbang_gotgot.member.controller;

import com.example.bangbang_gotgot.member.dto.AllUserInfoDto;
import com.example.bangbang_gotgot.member.dto.LoginDto;
import com.example.bangbang_gotgot.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberApiController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/bangbang/auth/sign-in")
    public ResponseEntity<String> joinProc(@RequestBody AllUserInfoDto allUserInfoDto) {
        memberService.createUser(allUserInfoDto);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입 완료");

    }

    @PostMapping("/bangbang/auth/sign-up")
    public ResponseEntity<String> loginProc(@RequestBody LoginDto loginDto) {

        return ResponseEntity.status(HttpStatus.OK).body("로그인 성공");
    }
}
