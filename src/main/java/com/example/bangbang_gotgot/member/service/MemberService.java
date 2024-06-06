package com.example.bangbang_gotgot.member.service;

import com.example.bangbang_gotgot.member.dto.AllUserInfoDto;
import com.example.bangbang_gotgot.member.dto.MemberDto;
import com.example.bangbang_gotgot.member.entity.Role;
import com.example.bangbang_gotgot.member.entity.User;
import com.example.bangbang_gotgot.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

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

    // 회원가입
    @Transactional
    public void createUser(AllUserInfoDto userAllInfoDto) {

        User user = new User();

        // 회원이 존재하지 않을때
        if (userAllInfoDto.getId() == null) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        // 해당 ID가 이미 존재하는지 확인
        if (userRepository.existsById(userAllInfoDto.getId())) {
            throw new IllegalArgumentException("이미 존재하는 회원 ID입니다.");
        }

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

}
