package com.example.bangbang_gotgot.article.service;

import com.example.bangbang_gotgot.article.entity.Article;
import com.example.bangbang_gotgot.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    // 글 작성 처리
    public void write(Article board) {
        articleRepository.save(board);
    }

    // 게시글 리스트 처리
    public Page<Article> list(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    // 특정 게시글 상세보기
    public Article view(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("없는 id입니다."));
    }

    // 특정 게시글 삭제
    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    public Page<Article> searchList(String searchKeyword, Pageable pageable) {
        return articleRepository.findByTitleContaining(searchKeyword, pageable);
    }
}
