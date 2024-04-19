package com.example.bangbang_gotgot.member.controller;

import com.example.bangbang_gotgot.member.dto.AllUserInfoDto;
import com.example.bangbang_gotgot.member.dto.LoginDto;
import com.example.bangbang_gotgot.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberApiController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/bangbang/auth/2/sign-in")
    public ResponseEntity<String> joinProc2(@RequestBody AllUserInfoDto allUserInfoDto) {
        memberService.createUser(allUserInfoDto);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입 완료");

    }

    // 회원가입 중복 체크
    @PostMapping("/bangbang/check/id")
    public ResponseEntity<Boolean> checkId(@RequestParam("person_id") String person_id){
        boolean checked = memberService.checkId(person_id);
        return ResponseEntity.status(HttpStatus.OK).body(checked);
    }

    @PostMapping("/bangbang/check/nickname")
    public ResponseEntity<Boolean> checkNick(@RequestParam("nick_name")String nick_name){
        boolean checked = memberService.checkNick(nick_name);
        return ResponseEntity.status(HttpStatus.OK).body(checked);
    }


    @PostMapping("/bangbang/auth/sign-up")
    public ResponseEntity<String> loginProc(@RequestBody LoginDto loginDto) {

        return ResponseEntity.status(HttpStatus.OK).body("로그인 성공");
    }
}