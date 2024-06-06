package com.example.bangbang_gotgot.article.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 이미지 저장 경로 설정
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private String resourcePath = "/upload/**"; // view 에서 접근할 경로
    private String savePath = "file:///C:/springboot_img/"; // 실제 파일 저장 경로

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcePath)
                .addResourceLocations(savePath);
    }

}
