package com.example.bangbang_gotgot.article.entity;

import com.example.bangbang_gotgot.member.entity.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Getter
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    // Getters, Setters, Constructors

    // 생성자에서 createdAt 초기화
    public Review(String content,String title, Article article) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.article = article;
    }

    // 기본 생성자
//    protected Review() {}
    public void setUserId(User user) {
        this.user = user;
    }

}
