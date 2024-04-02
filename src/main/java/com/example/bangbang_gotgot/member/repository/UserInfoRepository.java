package com.example.bangbang_gotgot.member.repository;

import com.example.bangbang_gotgot.member.entity.UserInfo;
import com.example.bangbang_gotgot.member.entity.UserPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, UserPk> {
}
