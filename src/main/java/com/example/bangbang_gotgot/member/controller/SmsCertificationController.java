package com.example.bangbang_gotgot.member.controller;

import com.example.bangbang_gotgot.member.controller.base.BaseController;
import com.example.bangbang_gotgot.member.dto.RedisUserDto;
import com.example.bangbang_gotgot.member.dto.base.DefaultRes;
import com.example.bangbang_gotgot.member.exception.CustomExceptions;
import com.example.bangbang_gotgot.member.exception.ResponseMessage;
import com.example.bangbang_gotgot.member.exception.StatusCode;
import com.example.bangbang_gotgot.member.service.UserCertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sms-certification")
public class SmsCertificationController extends BaseController {
    private final UserCertificationService userService;

    // 문자 보내기
    @PostMapping("/send")
    public ResponseEntity<?> sendSms(@RequestBody RedisUserDto.SmsCertificationRequest requestDto) throws Exception {
        try {
            userService.sendSms(requestDto);
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SMS_CERT_MESSAGE_SUCCESS), HttpStatus.OK);
        } catch (CustomExceptions.Exception e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }

    //인증번호 확인
    @PostMapping("/confirm")
    public ResponseEntity<Void> SmsVerification(@RequestBody RedisUserDto.SmsCertificationRequest requestDto) throws Exception{
        try {
            userService.verifySms(requestDto);
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SMS_CERT_SUCCESS), HttpStatus.OK);
        } catch (CustomExceptions.Exception e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }


}
