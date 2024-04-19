package com.example.bangbang_gotgot.article.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(name="address", nullable = false)
    private String address;

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



    @Builder
    public Article(String title, String content, String writer, LocalDateTime workTime, String address, String phoneNumber){
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.workTime = workTime;
        this.address = address;
        this.phoneNumber=phoneNumber;
    }
}
