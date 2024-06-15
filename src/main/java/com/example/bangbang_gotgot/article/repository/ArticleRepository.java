package com.example.bangbang_gotgot.article.repository;

import com.example.bangbang_gotgot.article.entity.Article;
import com.example.bangbang_gotgot.member.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAll(Specification<Article> spec, Pageable pageable);
    Page<Article> findAll(Pageable pageable);
    Page<Article> findAllByOrderByLikesDesc(Pageable pageable);
    Page<Article> findAllByOrderByViewDesc( Pageable pageable);
    Page<Article> findByAddress2(String district, Pageable pageable);
    Page<Article> findByAddress2OrderByViewDesc(String district, Pageable pageable);
    Page<Article> findByAddress2OrderByLikesDesc(String district, Pageable pageable);
    @Query("SELECT a FROM Article a WHERE a.user = :userId")
    List<Article> findByUser(@Param("userId") User user);
    List<Article> findTop8ByOrderByViewDesc();
}
