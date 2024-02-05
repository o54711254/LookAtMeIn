package com.ssafy.lam.config;

import jakarta.servlet.MultipartConfigElement;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

// SpringBoot 3.X에서 multipart를 사용 시 생성

@Configuration
@RequiredArgsConstructor
public class MultipartConfig {

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("c:\\lamImages"); // 파일 저장 위치
        factory.setMaxRequestSize(DataSize.ofMegabytes(100L)); // 최대 파일 전송 크기
        factory.setMaxFileSize(DataSize.ofMegabytes(100L)); // 최대 파일 크기

        return factory.createMultipartConfig();
    }
}