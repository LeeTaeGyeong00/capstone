package com.example.bangbang_gotgot.member.dto;

import com.example.bangbang_gotgot.member.entity.Role;
import lombok.Data;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class LoginResponse implements Serializable {
    private Long id;
    private String person_id;
    private String nick_name;
    private Role role;
    private String phone_num;
}