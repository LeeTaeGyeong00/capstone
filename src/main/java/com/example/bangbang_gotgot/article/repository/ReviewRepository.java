package com.example.bangbang_gotgot.article.repository;

import com.example.bangbang_gotgot.article.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
