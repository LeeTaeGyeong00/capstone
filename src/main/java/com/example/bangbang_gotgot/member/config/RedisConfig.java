//package com.example.bangbang_gotgot.member.config;
//
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
//
//@EnableRedisHttpSession
//@Configuration
//public class RedisConfig {
//
//    @Value("${spring.redis.host}")
//    private String redisHost;
//    @Value("${spring.redis.port}")
//    private int redisPort;
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory(){
//        return new LettuceConnectionFactory(redisHost, redisPort);
//    }
//
//    @Bean
//    public StringRedisTemplate stringRedisTemplate() {
//        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
//        stringRedisTemplate.setConnectionFactory(redisConnectionFactory());
//        return stringRedisTemplate;
//    }
//}
