package com.example.bangbang_gotgot.member.controller.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // 예외 처리를 한번에 모아서 관리
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = Exception.class) // 모든 예외를 처리하는 메서드
    public ResponseEntity handleException(Exception e) {
        // 예외 처리 & 내부 서버 오류 반환
        return handleApiException(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 예외 응답 메서드
    protected ResponseEntity handleApiException(Exception e, HttpStatus status) {
        // 응답 맵
        Map<String, String> res = new HashMap<>();
        res.put("statusCode", "error");
        res.put("responseMessage", e.getMessage());
        logger.info("error:{}", e.getMessage());

        // 응답 엔티티 생성 반환
        return new ResponseEntity<>(res, status);
    }

}