package com.example.bangbang_gotgot.article.dto;


import com.example.bangbang_gotgot.article.entity.Article;
import com.example.bangbang_gotgot.member.entity.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class ArticleDto {

    private Long id;

    private String title;

    private String writer;

    private String content;

    private String address1;

    private String address2;

    private String address3;

    private String phoneNumber;

    private String startTime1;

    private String startTime2;

    private String endTime1;

    private String endTime2;


    public static Article makeArticle(ArticleDto board, User user) {
        return new Article(
                board.getId(),
                board.getTitle(),
                user,
                board.getWriter(),
                board.getContent(),
                LocalDateTime.now(),
                board.getAddress1(),
                board.getAddress2(),
                board.getAddress3(),
                board.getPhoneNumber(),
                0,
                0,
                LocalDateTime.now(),
                LocalDateTime.now(),
                board.getStartTime1(),
                board.getEndTime1()
        );
    }
}
