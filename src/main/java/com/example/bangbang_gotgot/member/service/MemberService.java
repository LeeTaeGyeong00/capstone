package com.example.bangbang_gotgot.member.service;

import com.example.bangbang_gotgot.member.dto.AllUserInfoDto;
import com.example.bangbang_gotgot.member.dto.MemberDto;
import com.example.bangbang_gotgot.member.entity.Role;
import com.example.bangbang_gotgot.member.entity.User;
import com.example.bangbang_gotgot.member.repository.UserRepository;
import com.example.bangbang_gotgot.article.util.SmsUtil;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public MemberDto login(String personId, String rawPassword) {
        User member = userRepository.findByPersonId(personId);
        if (member == null) {
            throw new IllegalArgumentException("Invalid personId or password");
        }

        if (!bCryptPasswordEncoder.matches(rawPassword, member.getPasswd())) {
            throw new IllegalArgumentException("Invalid personId or password");
        }

        return MemberDto.from(member);
    }


    public void createUser(AllUserInfoDto userAllInfoDto) {

        User user = new User();

//        boolean exists = userRepository.existsById(userAllInfoDto.getId());
//        // 예외 발생
//        if (exists)
//            try {
//                throw new IllegalAccessException("회원의 id가 없어야 합니다.");
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }

        user.setPerson_id(userAllInfoDto.getPerson_id());
        user.setPasswd(bCryptPasswordEncoder.encode(userAllInfoDto.getPasswd()));
        user.setCreated_at(LocalDateTime.now());
        user.setLast_id_changed(LocalDateTime.now());
        user.setLast_passwd_changed(LocalDateTime.now());
        user.setRole(Role.ROLE_USER);
        user.setNick_name(userAllInfoDto.getNick_name());
        user.setOld(userAllInfoDto.getOld());
        user.setPhone_num(userAllInfoDto.getPhone_num());

        userRepository.save(user);


    }

    public boolean checkId(String person_id) {
        boolean existId = userRepository.existsByPersonId(person_id);
        if(existId){
            return false;
        } else {
            return true;
        }
    }


    public boolean checkNick(String nickName) {
        boolean existId = userRepository.existsByNickname(nickName);
        if(existId){
            return false;
        } else {
            return true;
        }
    }

//    @Autowired
//    private SmsUtil smsUtil;
//
//    public ResponseEntity<?> sendSmsToFindEmail(FindEmailRequestDto requestDto) {
//        String name = requestDto.getName();
//        //수신번호 형태에 맞춰 "-"을 ""로 변환
//        String phoneNum = requestDto.getPhoneNum().replaceAll("-","");
//
//        User foundUser = userRepository.findByNameAndPhone(name, phoneNum).orElseThrow(()->
//                new NoSuchElementException("회원이 존재하지 않습니다."));
//
//        String receiverEmail = foundUser.getEmail();
//        String verificationCode = validationUtil.createCode();
//        smsUtil.sendOne(phoneNum, verificationCode);
//
//        //인증코드 유효기간 5분 설정
//        redisUtil.setDataExpire(verificationCode, receiverEmail, 60 * 5L);
//
//        return ResponseEntity.ok(new Message("SMS 전송 성공"));
//    }
}
