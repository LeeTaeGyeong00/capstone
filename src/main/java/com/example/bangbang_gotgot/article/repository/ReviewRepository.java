package com.example.bangbang_gotgot.article.repository;

import com.example.bangbang_gotgot.article.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByArticleId(Long articleId);
}
