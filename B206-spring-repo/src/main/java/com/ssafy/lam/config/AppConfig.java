package com.ssafy.lam.config;

import com.ssafy.lam.interceptor.JWTInterceptor;
import com.ssafy.lam.util.JWTUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAspectJAutoProxy
@MapperScan(basePackages = { "com.ssafy.lam.customer.model.mapper" })
@ComponentScan(basePackages = { "com.ssafy.lam" })
public class AppConfig implements WebMvcConfigurer {

    private final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    private JWTInterceptor jwtInterceptor;

    @Autowired
    public AppConfig(JWTInterceptor jwtInterceptor) {
        super();
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // TODO Auto-generated method stub
        registry.addMapping("/**").allowedOrigins("*")
//		.allowedOrigins("http://localhost:5173", "http://localhost:5174")
                .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name(), HttpMethod.HEAD.name(), HttpMethod.OPTIONS.name(),
                        HttpMethod.PATCH.name())
//		.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD")
//		.allowCredentials(true)
//		.exposedHeaders("*")
                .maxAge(1800); // Pre-flight Caching
    }

}
