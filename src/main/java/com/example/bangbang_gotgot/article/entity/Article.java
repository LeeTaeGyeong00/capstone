package com.example.bangbang_gotgot.article.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="article_id", updatable = false)
    private Long id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="writer")
    private String writer;

    @Column(name="content", nullable = false)
    private String content;

    @Column(name="workTime", updatable = false)
    private LocalDateTime workTime;

    @Column(name="address1", nullable = false)
    private String address1;

    @Column(name="address2", nullable = false)
    private String address2;

    @Column(name="address3", nullable = false)
    private String address3;

    @Column(name="phoneNumber", nullable = false)
    private String phoneNumber;

    @ColumnDefault("0")
    private int likes;

    @ColumnDefault("0")
    private int view;

    @CreationTimestamp
    @Column(name="regDate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name="modDate")
    private LocalDateTime modDate;


//    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
//    @JsonManagedReference
//    private List<ArticleFile> boardFileList = new ArrayList<>();



    @Builder
    public Article(String title, String content, String writer, LocalDateTime workTime, String address1, String address2, String address3, String phoneNumber){
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.workTime = workTime;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.phoneNumber=phoneNumber;
    }

    public Article newArticle(Article article, int count) {
        return new Article(
                article.getId(),
                article.getTitle(),
                article.getWriter(),
                article.getContent(),
                article.getWorkTime(),
                article.getAddress1(),
                article.getAddress2(),
                article.getAddress3(),
                article.getPhoneNumber(),
                article.getLikes(),
                count,
                article.getRegDate(),
                article.getModDate()
//                article.getBoardFileList()
        );
    }
}
