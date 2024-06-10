package com.example.bangbang_gotgot.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String person_id;

    private String password;
}