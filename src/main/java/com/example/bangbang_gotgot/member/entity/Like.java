package com.example.bangbang_gotgot.member.entity;

import com.example.bangbang_gotgot.article.entity.Article;
import lombok.*;

import javax.persistence.*;
import java.lang.reflect.Member;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_no")
    private Long likeNo;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "article_no", updatable = false)
    private Article articleNo;

}
