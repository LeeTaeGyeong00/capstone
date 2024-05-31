package com.example.bangbang_gotgot.article.repository;

import com.example.bangbang_gotgot.article.entity.ArticleFile;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleFileRepository extends JpaRepository<ArticleFile, Long> {
//    @Query("SELECT af FROM ArticleFile af WHERE af.article.id = :articleId")
    @Query(value = "select * from article_file where article_id = :articleId", nativeQuery = true)
    List<ArticleFile> findByArticleId(@Param("articleId") Long articleId);

}
