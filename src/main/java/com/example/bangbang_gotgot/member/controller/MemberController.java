package com.example.bangbang_gotgot.member.controller;

import com.example.bangbang_gotgot.member.dto.AllUserInfoDto;
import com.example.bangbang_gotgot.member.dto.MemberDto;
import com.example.bangbang_gotgot.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    // 회원가입
    @GetMapping("/bangbang/auth/sign-in")
    public String join(){

        return "member/signUp/signUp";
    }

    @PostMapping("/bangbang/auth/sign-in")
    public String joinProc(AllUserInfoDto allUserInfoDto, Model model) {
        model.addAttribute("uesrInfo",allUserInfoDto);
        return "member/signUp/signUp2";
    }



    // 로그인

    @GetMapping("/bangbang/auth/sign-up")
    public String loginForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "member/login";
    }

    @PostMapping("/bangbang/auth/sign-up")
    public String login(MemberDto memberDto) {
        try {
            MemberDto loggedInMember = memberService.login(memberDto.getPerson_id(), memberDto.getPasswd());
            // 세션에 로그인 정보 저장
            // ...
            return "redirect:/"; // 메인 페이지로 리디렉션
        } catch (IllegalArgumentException e) {
            // 로그인 실패 시 처리
            return "redirect:/error/404";
        }
    }

    // 아이디 찾기
    @GetMapping("/bangbang/find-id")
    public String findId(){
        return "member/signFind/findId";
    }

    @PostMapping("/bangbang/find-id")
    public String findId_proc(Model model, @RequestParam("nickname")String nickname)
    {
//        String id = memberService.findRealId(nickname);
//        System.out.println("1111");
//        model.addAttribute("id",id);
        return "member/signFind/findId2";
    }

    // 비밀번호 찾기
    @GetMapping("/bangbang/find-pwd")
    public String findPwd(){
        return "member/signFind/findPwd";
    }

    @PostMapping("/bangbang/find-pwd")
    public String findPwd_proc(Model model, @RequestParam("personId")String personId,
                               @RequestParam("nickname")String nickname, @RequestParam("phone")String phone)
    {
        return "member/signFind/findPwd2";
    }



}
