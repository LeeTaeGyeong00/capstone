//package com.example.bangbang_gotgot.member.service;
//
//import com.example.bangbang_gotgot.member.dto.RedisUserDto;
//import com.example.bangbang_gotgot.member.exception.CustomExceptions;
//import com.example.bangbang_gotgot.member.repository.SmsCertificationDao;
//import com.example.bangbang_gotgot.member.util.SmsCertificationUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//
//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//public class UserServiceImpl implements UserCertificationService {
//
//    private final SmsCertificationUtil smsUtil;
//    private final SmsCertificationDao smsCertificationDao;
//
//    // redis 데이터 생성 & 문자 보내기
//    @Override
//    public void sendSms(RedisUserDto.SmsCertificationRequest requestDto) {
//        String to = requestDto.getPhone_num();
//        int randomNumber = (int) (Math.random() * 9000) + 1000;
//        String certificationNumber = String.valueOf(randomNumber);
//
//        smsUtil.sendSms(to, certificationNumber);
//        smsCertificationDao.createSmsCertification(to, certificationNumber);
//
//    }
//
//    // redis 데이터 지우기
//    @Override
//    public void verifySms(RedisUserDto.SmsCertificationRequest requestDto) {
//        if (isVerify(requestDto)) {
//            throw new CustomExceptions.SmsCertificationNumberMismatchException("인증번호가 일치하지 않습니다.");
//        }
//        smsCertificationDao.removeSmsCertification(requestDto.getPhone_num());
//    }
//
//    // 문자 인증이 되었는지 확인
//    @Override
//    public boolean isVerify(RedisUserDto.SmsCertificationRequest requestDto) {
//        return !(smsCertificationDao.hasKey(requestDto.getPhone_num()) &&
//                smsCertificationDao.getSmsCertification(requestDto.getPhone_num())
//                        .equals(requestDto.getCertificationNumber()));
//    }
//
//
//}
