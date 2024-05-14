package com.example.bangbang_gotgot.article.service;

import com.example.bangbang_gotgot.article.entity.Article;
import com.example.bangbang_gotgot.article.repository.ArticleRepository;
import com.example.bangbang_gotgot.article.specification.ArticleSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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
        Page<Article> result = articleRepository.findAll(spec, pageable);


        if (Objects.equals(option, "1")) {
            return result;
        } else if (Objects.equals(option, "2")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("view").descending());
            return articleRepository.findAll(spec, pageable);

        } else if (Objects.equals(option, "3")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("likes").descending());
            return articleRepository.findAll(spec, pageable);
        }
        return result;

    }

    public Page<Article> searchList2(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }


    public Page<Article> searchList3(String searchKeyword, Pageable pageable) {
        Specification<Article> spec = ArticleSpecifications.searchByKeyword(searchKeyword);
        return articleRepository.findAll(spec, pageable);
    }

    public Page<Article> locate(Pageable pageable, String district, String option) {

        if (Objects.equals(option, "1")) {
            return articleRepository.findByAddress2(district, pageable);
        } else if (Objects.equals(option, "2")) {
            return articleRepository.findByAddress2OrderByViewDesc(district, pageable);
        } else if (Objects.equals(option, "3")) {
            return articleRepository.findByAddress2OrderByLikesDesc(district, pageable);
        }
        else {
            return articleRepository.findByAddress2(district, pageable);
        }

    }


    public Page<Article> searchList4(Pageable pageable, String option) {

        if (Objects.equals(option, "1")) {
            return articleRepository.findAll(pageable);
        } else if (Objects.equals(option, "2")) {
            return articleRepository.findAllByOrderByViewDesc(pageable);
        } else if (Objects.equals(option, "3")) {
            return articleRepository.findAllByOrderByLikesDesc(pageable);
        }
        return articleRepository.findAll(pageable);
    }
}
