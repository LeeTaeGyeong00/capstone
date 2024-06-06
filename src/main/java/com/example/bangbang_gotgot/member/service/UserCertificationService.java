package com.example.bangbang_gotgot.member.service;

import com.example.bangbang_gotgot.member.dto.RedisUserDto;

public interface UserCertificationService {
    void sendSms(RedisUserDto.SmsCertificationRequest requestDto);
    void verifySms(RedisUserDto.SmsCertificationRequest requestDto);
    boolean isVerify(RedisUserDto.SmsCertificationRequest requestDto);

}
