package com.example.bangbang_gotgot.article.repository;

import com.example.bangbang_gotgot.article.entity.Article;
import com.example.bangbang_gotgot.article.entity.Review;
import com.example.bangbang_gotgot.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByArticleIdOrderByIdDesc(Long articleId);

    List<Review> findByArticleIdOrderByIdAsc(Long articleId);

    List<Review> findByUser(User user);
}
