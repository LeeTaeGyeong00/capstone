package com.example.bangbang_gotgot.member.repository;

import com.example.bangbang_gotgot.article.entity.Review;
import com.example.bangbang_gotgot.member.entity.Like;
import com.example.bangbang_gotgot.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
     boolean existsByUserIdAndArticleNoId(Long memberNo, Long articleNo);

     List<Like> findByUser(User user);
}
