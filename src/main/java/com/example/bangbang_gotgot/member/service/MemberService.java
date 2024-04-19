package com.example.bangbang_gotgot.member.service;

import com.example.bangbang_gotgot.member.dto.AllUserInfoDto;
import com.example.bangbang_gotgot.member.dto.MemberDto;
import com.example.bangbang_gotgot.member.entity.Role;
import com.example.bangbang_gotgot.member.entity.User;
import com.example.bangbang_gotgot.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

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
}
