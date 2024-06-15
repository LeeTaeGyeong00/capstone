package com.example.bangbang_gotgot.member.repository;

import com.example.bangbang_gotgot.member.entity.Like;
import com.example.bangbang_gotgot.member.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
     @Query("SELECT l FROM Like l WHERE l.user = :userId")
     List<Like> findByUser(@Param("userId") User user);
     //List<Like> findByArticleNo(Long articleId);
     //boolean existsByUserAndArticleNoId(Long memberNo, Long articleNo);
}
