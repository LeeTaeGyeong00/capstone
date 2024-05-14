package com.example.bangbang_gotgot.article.specification;


import com.example.bangbang_gotgot.article.entity.Article;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class ArticleSpecifications {
    public static Specification<Article> searchByKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {  // criteria: JPA기능 중 하나로 동적 쿼리 생성
            String likeKeyword = "%" + keyword + "%";
            Predicate titlePredicate = criteriaBuilder.like(root.get("title"), likeKeyword); //predicate: sql where절
            Predicate contentPredicate = criteriaBuilder.like(root.get("address1"), likeKeyword);
            Predicate contentPredicate2 = criteriaBuilder.like(root.get("address2"), likeKeyword);

            return criteriaBuilder.or(titlePredicate, contentPredicate, contentPredicate2);
        };
    }
}
