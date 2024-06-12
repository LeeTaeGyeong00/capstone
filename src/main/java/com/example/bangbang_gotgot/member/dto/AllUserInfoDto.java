package com.example.bangbang_gotgot.member.dto;

import com.example.bangbang_gotgot.member.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AllUserInfoDto {

    private Long id;

    private String person_id;

    private  String passwd;

    private String nick_name;

    private int old;

    private String phone_num;


}
