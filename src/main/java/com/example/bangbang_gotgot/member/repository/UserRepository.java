package com.example.bangbang_gotgot.member.repository;

import com.example.bangbang_gotgot.member.entity.User;
import com.example.bangbang_gotgot.member.entity.UserPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UserPk> {
}
