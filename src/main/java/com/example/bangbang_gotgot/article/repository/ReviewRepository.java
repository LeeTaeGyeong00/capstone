package com.example.bangbang_gotgot.article.repository;

import com.example.bangbang_gotgot.article.entity.Article;
import com.example.bangbang_gotgot.article.entity.Review;
import com.example.bangbang_gotgot.member.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByArticleId(Long articleId);
    @Query("SELECT a FROM Article a WHERE a.user = :userId")
    List<Review> findByUser(@Param("userId") User user);
}
