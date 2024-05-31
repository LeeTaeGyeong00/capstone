package com.example.bangbang_gotgot.article.controller;

import com.example.bangbang_gotgot.article.dto.BoardDTO;
import com.example.bangbang_gotgot.article.entity.Article;
import com.example.bangbang_gotgot.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/")
    public String main(Model model){
        List<Article> articles = articleService.articleRanking();
        List<BoardDTO> boardDTOS = articleService.articleRankingFiles();
        model.addAttribute("articles", articles);
        model.addAttribute("files", boardDTOS);
        return "index";
    }

}
