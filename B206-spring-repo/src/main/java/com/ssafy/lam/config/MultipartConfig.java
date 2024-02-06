package com.ssafy.lam.config;

import jakarta.servlet.MultipartConfigElement;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import java.io.File;

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
        String uploadDir = System.getProperty("user.dir");

        System.out.println("uploadDir = " + uploadDir);
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String location = uploadDir + File.separator + "images";
//        String location = "/images";
        factory.setLocation(location); // 파일 저장 위치
        factory.setMaxRequestSize(DataSize.ofMegabytes(100L)); // 최대 파일 전송 크기
        factory.setMaxFileSize(DataSize.ofMegabytes(100L)); // 최대 파일 크기

        return factory.createMultipartConfig();
    }
}