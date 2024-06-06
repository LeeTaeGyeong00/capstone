//package com.example.bangbang_gotgot.member.repository;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.time.Duration;
//
//@RequiredArgsConstructor
//@Repository
//public class SmsCertificationDao {
//
//    private final String PREFIX = "sms:";
//    private final int LIMIT_TIME = 3 * 60; // 제한시간 3분
//
//    private final StringRedisTemplate redisTemplate; // redis 서버 연결
//
//    // key: 전화번호, value: 인증번호 생성
//    public void createSmsCertification(String phone, String certificationNumber) {
//        redisTemplate.opsForValue()
//                .set(PREFIX + phone, certificationNumber, Duration.ofSeconds(LIMIT_TIME));
//    }
//
//    // 전화번호로 인증번호 찾기
//    public String getSmsCertification(String phone) {
//        return redisTemplate.opsForValue().get(PREFIX + phone);
//    }
//
//    // 전화번호로 key, value 제거
//    public void removeSmsCertification(String phone) {
//        redisTemplate.delete(PREFIX + phone);
//    }
//
//    // 데이터베이스에 존재하는지 존재여부 묻기
//    public boolean hasKey(String phone) {
//        return redisTemplate.hasKey(PREFIX + phone);
//    }
//}
