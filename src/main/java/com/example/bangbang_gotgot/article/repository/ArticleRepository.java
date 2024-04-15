package com.example.bangbang_gotgot.article.repository;

import com.example.bangbang_gotgot.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByTitleContaining(String searchKeyword, Pageable pageable);
}
