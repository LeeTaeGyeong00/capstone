package com.example.bangbang_gotgot.article.controller;

import com.example.bangbang_gotgot.article.dto.ReviewDto;
import com.example.bangbang_gotgot.article.entity.Article;
import com.example.bangbang_gotgot.article.entity.Review;
import com.example.bangbang_gotgot.article.service.ArticleService;
import com.example.bangbang_gotgot.article.service.ReviewService;
import com.example.bangbang_gotgot.member.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@RestController
@Slf4j
@RequiredArgsConstructor
@Controller
public class ReviewApiController {
    private final ReviewService reviewService;
    private final ArticleService articleService;

    @PostMapping("/review-write-post/{articleId}")
    public ResponseEntity<String> writeReview(@PathVariable Long articleId,
                                              @RequestBody ReviewDto reviewDto,
                                              HttpSession session,
                                              RedirectAttributes redirectAttributes) {
        log.info("Received review write request for articleId: {}", articleId);

        // 세션에서 현재 로그인한 사용자의 정보 가져오기
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            // 로그인되지 않은 사용자일 경우 로그인 페이지로 리다이렉트
            log.info("User not logged in. Redirecting to login page.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        log.info("User logged in: {}", sessionUser.getId());

        // Article 조회
        Article article = articleService.findArticleById(articleId);
        if (article == null) {
            log.error("Article not found for articleId: {}", articleId);
            throw new IllegalArgumentException("Invalid article ID");
        }

        // 리뷰 작성 및 저장
        Review review = new Review(reviewDto.getContent(), reviewDto.getTitle(), article);
        review.setUserId(sessionUser); // 리뷰를 작성한 사용자 설정
        reviewService.saveReview(review);

        // RedirectAttributes를 사용하여 articleId를 전달
        redirectAttributes.addAttribute("id", articleId);

        log.info("Review saved for articleId: {}", articleId);

        return ResponseEntity.ok("Review saved successfully");
    }


    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
