package com.example.bangbang_gotgot.member.controller;

import com.example.bangbang_gotgot.member.dto.AllUserInfoDto;
import com.example.bangbang_gotgot.member.dto.MemberDto;
import com.example.bangbang_gotgot.member.entity.User;
import com.example.bangbang_gotgot.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public  String loginPage(){
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


    // 마이페이지
    @GetMapping("/bangbang/myPage")
    public String myPage() {
        return "myPage/UserPage";
    }

    // 회원정보 수정
    @GetMapping("/bangbang/user/{id}/update")
    public String update(@PathVariable Long id, Model model){
        User user = memberService.findUser(id);
        if (user == null) {
            return "error/404";
        }
        model.addAttribute("user",user);
        return "myPage/UserUpdate/userUpdate1";
    }

    // 회원정보 수정2
    @PostMapping("/bangbang/user/{id}/update")
    public String update2(AllUserInfoDto allUserInfoDto, Model model) {
        model.addAttribute("user",allUserInfoDto);
        return "myPage/UserUpdate/userUpdate2";
    }

    // 회원정보 수정 완료



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
    public String findPwd_proc(Model model, @RequestParam("personId")String personId)
    {
        String newPwd = memberService.updatePassword(personId); // 임시 비밀번호 생성
        model.addAttribute("pwd",newPwd);
        return "member/signFind/findPwd2";
    }



}
