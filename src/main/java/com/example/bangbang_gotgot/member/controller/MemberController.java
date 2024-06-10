package com.example.bangbang_gotgot.member.controller;

import com.example.bangbang_gotgot.member.dto.AllUserInfoDto;
import com.example.bangbang_gotgot.member.dto.LoginRequest;
import com.example.bangbang_gotgot.member.dto.LoginResponse;
import com.example.bangbang_gotgot.member.dto.MemberDto;
import com.example.bangbang_gotgot.member.entity.User;
import com.example.bangbang_gotgot.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public  String loginPage(Model model){
        model.addAttribute("memberDto", new MemberDto());
        return "member/login";
    }

//    @PostMapping("/bangbang/auth/login")
//    public String login(@ModelAttribute LoginRequest loginRequest, HttpServletRequest request, Model model) {
//        try {
//            User user = memberService.login(loginRequest);
//
//            HttpSession session = request.getSession(true);
//            session.setAttribute("user", user);
//
//            return "redirect:/";
//        } catch (Exception e) {
//            model.addAttribute("error", "Invalid username or password");
//            model.addAttribute("loginRequest", new LoginRequest());
//            return "member/login";
//        }
//    }

    @PostMapping("/bangbang/auth/login")
    public String login(@ModelAttribute MemberDto memberDto, HttpServletRequest request, Model model) {
        try {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setPerson_id(memberDto.getPerson_id());
            loginRequest.setPassword(memberDto.getPasswd()); // 수정: 필드 이름을 올바르게 사용

            User user = memberService.login(loginRequest); // 수정: User 객체 반환

            HttpSession session = request.getSession(true);
            session.setAttribute("user", user); // 수정: User 객체를 세션에 저장

            // 로그인 성공 시 홈 페이지로 리다이렉트
            return "redirect:/";
        } catch (Exception e) {
            // 로그인 실패 시 로그인 페이지로 리다이렉트하고 에러 메시지를 보여줌
            model.addAttribute("error", "Invalid username or password");
            model.addAttribute("memberDto", memberDto); // 여기서 new MemberDto() 대신 기존 memberDto를 넘겨줌
            return "member/login";
        }
    }
    // 마이페이지
    @GetMapping("/bangbang/myPage")
    public String myPage(Model model,HttpSession session) {
        Object sessionUser = session.getAttribute("user");
        model.addAttribute("user", sessionUser);
        return "myPage/UserPage";
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
    public String findPwd_proc(Model model, @RequestParam("personId")String personId)
    {
        String newPwd = memberService.updatePassword(personId); // 임시 비밀번호 생성
        model.addAttribute("pwd",newPwd);
        return "member/signFind/findPwd2";
    }


    //마이페이지
    @GetMapping("/user/info")
    public String getUserInfo(HttpSession session, Model model) {
        // 세션에서 사용자 정보 가져오기
        Object sessionUser = session.getAttribute("user");

        // 세션에 사용자 정보가 없으면 회원가입 페이지로 리다이렉트
        if (sessionUser == null) {
            return "redirect:/bangbang/auth/sign-up";
        }

        // 세션에 저장된 사용자 정보를 모델에 추가하여 페이지에 전달
        model.addAttribute("user", sessionUser);

        return "myPage/myPage";
    }
}
