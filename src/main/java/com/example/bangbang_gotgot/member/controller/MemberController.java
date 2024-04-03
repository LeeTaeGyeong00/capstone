package com.example.bangbang_gotgot.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    @GetMapping("/bangbang/auth/sign-in")
    public String join(){

        return "member/signUp/signUp";
    }

    @GetMapping("/bangbang/auth/sign-up")
    public String login(){

        return "member/login";
    }
}
