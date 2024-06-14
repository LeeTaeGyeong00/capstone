package com.example.bangbang_gotgot.member.repository;

import com.example.bangbang_gotgot.member.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface LikeRepository extends JpaRepository<Like, Long> {
     boolean existsByUserNoIdAndArticleNoId(Long memberNo, Long articleNo);
}
