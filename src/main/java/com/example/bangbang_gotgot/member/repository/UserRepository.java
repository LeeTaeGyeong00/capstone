package com.example.bangbang_gotgot.member.repository;

import com.example.bangbang_gotgot.member.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    // 스네이크 형식쓰면 이렇게 골치 아프게 작업해야 함...
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.person_id = :personId")
    boolean existsByPersonId(@Param("personId") String personId);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.nick_name = :nickName")
    boolean existsByNickname(@Param("nickName") String nickName);
}
