package com.example.bangbang_gotgot.article.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
@Entity
@Data
public class ArticleFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    public static ArticleFile toBoardFileEntity(Article article, String originalFileName, String storedFileName) {
        ArticleFile articleFile = new ArticleFile();
        articleFile.setOriginalFileName(originalFileName);
        articleFile.setStoredFileName(storedFileName);
        articleFile.setArticle(article);
        return articleFile;
    }

}
