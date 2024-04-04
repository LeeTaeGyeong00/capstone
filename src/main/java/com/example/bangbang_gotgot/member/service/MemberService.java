package com.example.bangbang_gotgot.member.service;

import com.example.bangbang_gotgot.member.dto.AllUserInfoDto;
import com.example.bangbang_gotgot.member.entity.AutoPk;
import com.example.bangbang_gotgot.member.entity.User;
import com.example.bangbang_gotgot.member.entity.UserInfo;
import com.example.bangbang_gotgot.member.entity.UserPk;
import com.example.bangbang_gotgot.member.repository.AutoPkRepository;
import com.example.bangbang_gotgot.member.repository.UserInfoRepository;
import com.example.bangbang_gotgot.member.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AutoPkRepository autoPkRepository;




    @Transactional
    public void createUser(AllUserInfoDto userAllInfoDto) {


        User user = new User();
        UserPk userPk = new UserPk();
        AutoPk autoPk = new AutoPk();

        // autoPk : 자동증가 id 저장하는 DB, 복합키는 Generatedvalue 제공 안됨
        int idd = autoPkRepository.findMaxId().orElse(0);
        userPk.setId(idd+1);
        autoPk.setId(idd+1);



        boolean exists = userRepository.existsById(userPk);
        // 예외 발생
        if (exists)
            try {
                throw new IllegalAccessException("회원의 id가 없어야 합니다.");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        user.setId(userPk);
        user.setPerson_id(userAllInfoDto.getPerson_id());
        user.setPasswd(bCryptPasswordEncoder.encode(userAllInfoDto.getPasswd()));
        user.setCreated_at(LocalDateTime.now());
        user.setLast_id_changed(LocalDateTime.now());
        user.setLast_passwd_changed(LocalDateTime.now());

        userRepository.save(user);
        autoPkRepository.save(autoPk);

        UserInfo userInfo = UserInfo.toEntity(userAllInfoDto, user);
        userInfoRepository.save(userInfo);


    }

}
