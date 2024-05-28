package com.example.bangbang_gotgot.article.repository;

import com.example.bangbang_gotgot.article.entity.ArticleFile;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleFileRepository extends JpaRepository<ArticleFile, Long> {
//    @Query("SELECT af FROM ArticleFile af WHERE af.article.id = :articleId")
    ArticleFile findByArticleId( Long articleId);
}
