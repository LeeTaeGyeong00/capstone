package com.example.bangbang_gotgot.article.service;

import com.example.bangbang_gotgot.article.entity.Article;
import com.example.bangbang_gotgot.article.repository.ArticleRepository;
import com.example.bangbang_gotgot.article.specification.ArticleSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    // 글 작성 처리
    public void write(Article board) {
        articleRepository.save(board);
    }

    // 게시글 리스트 처리
//    public Page<Article> list(Pageable pageable, String option) {
//        if (option.equals("1")) {
//            return articleRepository.findAll(pageable);
//        } else if (option.equals("2")) {
//            return articleRepository.findAllByOrderByViewDesc(pageable);
//        } else if (option.equals("3")) {
//            return articleRepository.findAllByOrderByLikesDesc(pageable);
//        }
//
//        return articleRepository.findAllByOrderByLikesDesc(pageable);
//    }

    // 특정 게시글 상세보기
    public Article view(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("없는 id입니다."));
    }

    // 특정 게시글 삭제
    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    public Page<Article> searchList(String searchKeyword, Pageable pageable, String option) {
        Specification<Article> spec = ArticleSpecifications.searchByKeyword(searchKeyword);

        if (option.equals("1") || option.isEmpty()) {
            return articleRepository.findAll(pageable);
        } else if (option.equals("2")) {
            return articleRepository.findAllByOrderByViewDesc(pageable);
        } else if (option.equals("3")) {
            return articleRepository.findAllByOrderByLikesDesc(pageable);
        }
        return articleRepository.findAll(pageable);
    }

    public Page<Article> searchList2(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }


    public Page<Article> searchList3(String searchKeyword, Pageable pageable) {
        Specification<Article> spec = ArticleSpecifications.searchByKeyword(searchKeyword);
        return articleRepository.findAll(spec, pageable);
    }
}
