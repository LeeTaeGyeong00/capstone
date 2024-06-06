package com.example.bangbang_gotgot.member.controller;

import com.example.bangbang_gotgot.member.controller.base.BaseController;
import com.example.bangbang_gotgot.member.dto.AllUserInfoDto;
import com.example.bangbang_gotgot.member.dto.base.DefaultRes;
import com.example.bangbang_gotgot.member.exception.CustomExceptions;
import com.example.bangbang_gotgot.member.exception.ResponseMessage;
import com.example.bangbang_gotgot.member.exception.StatusCode;
import com.example.bangbang_gotgot.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberApiController extends BaseController {

    @Autowired
    private MemberService memberService;

    // 회원가입
        @PostMapping("/bangbang/auth/2/sign-in")
        public ResponseEntity<?> joinProc2(@RequestBody AllUserInfoDto allUserInfoDto) {
            try {
                memberService.createUser(allUserInfoDto);
                return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.JOIN_MEMBER_SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            return handleApiException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 회원가입 중복 체크 : 아이디
    @PostMapping("/bangbang/check/id")
    public ResponseEntity<Boolean> checkId(@RequestParam("person_id") String person_id){
        try {
            boolean checked = memberService.checkId(person_id);
            return ResponseEntity.status(HttpStatus.OK).body(checked);
        } catch (CustomExceptions.Exception e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return handleApiException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 회원가입 중복 체크 : 닉네임
    @PostMapping("/bangbang/check/nickname")
    public ResponseEntity<Boolean> checkNick(@RequestParam("nick_name")String nick_name){
        try {
            boolean checked = memberService.checkNick(nick_name);
            return ResponseEntity.status(HttpStatus.OK).body(checked);
        } catch (CustomExceptions.Exception e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return handleApiException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // 회원 아이디 체크
    @PostMapping("/bangbang/find-id/check")
    public ResponseEntity<?> find_id(@RequestParam("nickname")String nickname,
                                     @RequestParam("phone")String phone)
    {
        try {
            Boolean target = memberService.findId(nickname, phone);
            if (target) {
//                return ResponseEntity.status(HttpStatus.OK).body(" ");
                return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.ID_PASSWORD_CHECK_SUCCESS), HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 정보가 일치하지 않습니다. ");
        } catch (CustomExceptions.Exception e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }

    // 회원 비밀번호 체크
    @PostMapping("/bangbang/find-Pwd/check")
    public ResponseEntity<?> find_pwd(@RequestParam("personId")String personId, @RequestParam("nickname")String nickname,
                                      @RequestParam("phone")String phone)
    {
        try {
            Boolean target = memberService.findPwd(personId, nickname, phone);
            if (target) {
//                return ResponseEntity.status(HttpStatus.OK).body(" ");
                return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.ID_PASSWORD_CHECK_SUCCESS), HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 정보가 일치하지 않습니다. ");
        } catch (CustomExceptions.Exception e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }


}