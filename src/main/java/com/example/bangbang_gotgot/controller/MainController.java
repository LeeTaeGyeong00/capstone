package com.example.bangbang_gotgot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
//2024.03.31 이태석
    @GetMapping("/")
    public String hello(){
        return "index";
    }
}
