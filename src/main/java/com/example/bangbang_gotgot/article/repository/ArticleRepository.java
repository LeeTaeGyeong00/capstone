package com.example.bangbang_gotgot.article.repository;

import com.example.bangbang_gotgot.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAll(Specification<Article> spec, Pageable pageable);
    Page<Article> findAllByOrderByLikesDesc(Pageable pageable);
    Page<Article> findAllByOrderByViewDesc(Pageable pageable);
}
