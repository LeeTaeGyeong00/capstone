package com.example.bangbang_gotgot.member.dto;

import lombok.Getter;

public class RedisUserDto {
    @Getter
    public static class SmsCertificationRequest {

        private String phone_num;
        private String certificationNumber;

    }
}
