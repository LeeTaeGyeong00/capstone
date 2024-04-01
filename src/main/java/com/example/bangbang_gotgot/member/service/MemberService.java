package com.example.bangbang_gotgot.member.service;

import com.example.bangbang_gotgot.member.dto.AllUserInfoDto;
import com.example.bangbang_gotgot.member.entity.User;
import com.example.bangbang_gotgot.member.entity.UserInfo;
import com.example.bangbang_gotgot.member.entity.UserPk;
import com.example.bangbang_gotgot.member.repository.UserInfoRepository;
import com.example.bangbang_gotgot.member.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class MemberService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    private int i = 0;
    @Transactional
    public void createUser(AllUserInfoDto userAllInfoDto) {

        i++;

        UserPk userPk = new UserPk();
        userPk.setId(i);

        Boolean exists = userRepository.existsById(userPk);
        // 예외 발생
        if (exists)
            try {
                throw new IllegalAccessException("회원의 id가 없어야 합니다.");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        User user = new User();

        user.setId(userPk);
        user.setPerson_id(userAllInfoDto.getPerson_id());
        user.setPasswd(bCryptPasswordEncoder.encode(userAllInfoDto.getPasswd()));
        user.setCreated_at(LocalDateTime.now());
        user.setLast_id_changed(LocalDateTime.now());
        user.setLast_passwd_changed(LocalDateTime.now());

        userRepository.save(user);

        UserInfo userInfo = UserInfo.toEntity(userAllInfoDto, user);
        userInfoRepository.save(userInfo);


    }

}
