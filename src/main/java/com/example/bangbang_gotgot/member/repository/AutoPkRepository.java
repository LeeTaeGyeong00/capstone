package com.example.bangbang_gotgot.member.repository;

import com.example.bangbang_gotgot.member.entity.AutoPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutoPkRepository extends JpaRepository<AutoPk, Integer> {

//    @Query("select id from AutoPk  order by id desc limit 1") 안됨 ...
//    int findMaxId();

    // 배운점 : like, limit 못 씀, 아무래도 specification(동적쿼리 생성)을 이용해야하나 보다
    @Query("select max(id) from AutoPk")
    Optional<Integer> findMaxId();

}