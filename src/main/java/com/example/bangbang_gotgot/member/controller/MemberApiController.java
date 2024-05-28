package com.example.bangbang_gotgot.member.controller;

import com.example.bangbang_gotgot.member.dto.AllUserInfoDto;
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


    @PostMapping("/sign-up")
    public ResponseEntity<String> loginProc()
    {
        return ResponseEntity.status(HttpStatus.OK).body("로그인 성공");
    }

    // 회원 아이디 체크
    @PostMapping("/bangbang/find-id/check")
    public ResponseEntity<?> find_id(Model model, @RequestParam("nickname")String nickname,
                                     @RequestParam("phone")String phone)
    {
        System.out.println("2222");
        Boolean target = memberService.findId(nickname, phone);
        if (target) {
            return ResponseEntity.status(HttpStatus.OK).body(" ");
        }
        System.out.println("111111111");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" ");
    }


}