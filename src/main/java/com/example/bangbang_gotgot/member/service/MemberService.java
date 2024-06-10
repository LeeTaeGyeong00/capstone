package com.example.bangbang_gotgot.member.service;

import com.example.bangbang_gotgot.member.dto.AllUserInfoDto;
import com.example.bangbang_gotgot.member.dto.LoginRequest;
import com.example.bangbang_gotgot.member.dto.MemberDto;
import com.example.bangbang_gotgot.member.entity.Role;
import com.example.bangbang_gotgot.member.entity.User;
import com.example.bangbang_gotgot.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public User login(LoginRequest loginRequest) throws Exception {
        System.out.println(loginRequest);
        // 사용자 조회
        User user = userRepository.findByPersonId(loginRequest.getPerson_id()); // 수정: 필드 이름을 올바르게 사용
        if (user == null) {
            throw new Exception("User not found");
        }

        // 비밀번호 확인
        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPasswd())) { // 수정: 필드 이름을 올바르게 사용
            throw new Exception("Invalid password");
        }

        // 로그인 성공 시 사용자 객체 반환
        return user;
    }


    // 회원가입
    @Transactional
    public void createUser(AllUserInfoDto userAllInfoDto) {

        User user = new User();

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

    // 중복된 아이디 확인
    public boolean checkId(String person_id) {
        boolean existId = userRepository.existsByPersonId(person_id);
        if(existId){
            return false;
        } else {
            return true;
        }
    }

    // 중복된 닉네임 확인
    public boolean checkNick(String nickName) {
        boolean existId = userRepository.existsByNickname(nickName);
        if(existId){
            return false;
        } else {
            return true;
        }
    }


    // 아이디 찾기(api)
    public Boolean findId(String nickname, String phone) {
        User user = userRepository.findByNickName(nickname);
        if(user == null){
            return false;
        }
        else {
            if(user.getPhone_num().equals(phone) && !phone.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    // 아이디 찾기
    public String findRealId(String nickname) {
        User user = userRepository.findByNickName(nickname);
        return user.getPerson_id();
    }


    // 비밀번호 찾기 (api)
    public Boolean findPwd(String personId, String nickname, String phone) {
        User user = userRepository.findByNickName(nickname);
        if(user == null){
            return false;
        }
        else {
            if(user.getPhone_num().equals(phone) && user.getPerson_id().equals(personId) && !phone.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    // 임시 비밀번호 설정
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";

    private static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
    private static SecureRandom random = new SecureRandom();

    // 임시 비밀번호 생성
    private static String generateTemporaryPassword(int length) {
        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
            sb.append(rndChar);
        }

        return sb.toString();
    }

    // 임시 비밀번호 업데이트
    @Transactional
    public String updatePassword(String personId) {
        int length = 10;
        String temporaryPassword = generateTemporaryPassword(length);
        System.out.println("새 비밀번호: "+ temporaryPassword);

        // 사용자를 조회
        User user = userRepository.findByPersonId(personId);

        // 사용자가 존재하지 않는 경우 예외 처리
        if (user == null) {
            throw new IllegalArgumentException("해당 personId를 가진 사용자를 찾을 수 없습니다.");
        }

        // 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(temporaryPassword);

        // 업데이트
        user.setPasswd(encryptedPassword);
        user.setLast_passwd_changed(LocalDateTime.now());

        userRepository.save(user);

        return temporaryPassword;
    }
//    @Transactional
//    public MyPageResponse getMyPageResponse(Long userId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//
//        MyPageResponse myPageResponse = new MyPageResponse();
//        myPageResponse.setId(user.getId());
//        myPageResponse.setPerson_id(user.getPerson_id());
//        myPageResponse.setNick_name(user.getNick_name());
//        myPageResponse.setPhone_num(user.getPhone_num());
//
//        System.out.println(myPageResponse.getId());
//        System.out.println(myPageResponse.getNick_name());
//        System.out.println(myPageResponse.getPerson_id());
//        System.out.println(myPageResponse.getPhone_num());
//        return myPageResponse;
//    }
}
