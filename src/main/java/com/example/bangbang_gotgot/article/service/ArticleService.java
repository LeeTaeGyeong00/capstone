package com.example.bangbang_gotgot.article.service;

import com.example.bangbang_gotgot.article.dto.BoardDTO;
import com.example.bangbang_gotgot.article.entity.Article;
import com.example.bangbang_gotgot.article.entity.ArticleFile;
import com.example.bangbang_gotgot.article.repository.ArticleFileRepository;
import com.example.bangbang_gotgot.article.repository.ArticleRepository;
import com.example.bangbang_gotgot.article.specification.ArticleSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleFileRepository articleFileRepository;

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

    // 게시글 목록: 검색
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

    // 게시글 목록: 지역
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

    // 상세 페이지
    public Article findIdList(Long id, HttpServletRequest request, HttpServletResponse response) {
        Article article = articleRepository.findById(id).orElse(null);
        Long articleId = article.getId();

        int count = article.getView();
        if (!isArticleIdInCookies(request, id)) {
            count++;
            Article target = article.newArticle(article, count);
            System.out.println(target);
            articleRepository.save(target);

            // 쿠키에 게시물 ID 추가 (중복 조회 방지)
            addArticleIdToCookies(response, id);
        }
        System.out.println(article);

        return article;
    }

    // 쿠키에 게시물 ID 추가 (중복 조회 방지)
    private void addArticleIdToCookies(HttpServletResponse response, Long articleId) {
        Cookie cookie = new Cookie("viewed_article_" + articleId, "true");
        cookie.setMaxAge(24 * 60 * 60); // 1일 유지
        response.addCookie(cookie);
    }


    // 쿠키에서 해당 게시물 ID가 있는지 확인
    private boolean isArticleIdInCookies(HttpServletRequest request, Long articleId) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (("viewed_article_" + articleId).equals(cookie.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    // id에 해당하는 파일 찾기
    public BoardDTO findFile(Long id) {
        ArticleFile articleFile = articleFileRepository.findByArticleId(id);
        System.out.println(articleFile);
        BoardDTO boardDTO = BoardDTO.toBoardDTO(articleFile);
        return boardDTO;
    }

}
