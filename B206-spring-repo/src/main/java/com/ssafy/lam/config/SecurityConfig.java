package com.ssafy.lam.config;

import com.ssafy.lam.interceptor.JwtAuthenticationFilter;
import com.ssafy.lam.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .cors((cors) -> cors.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration configuration = new CorsConfiguration();
                        configuration.setAllowedOrigins(Collections.singletonList("*"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);      // 1시간 동안 캐시하도록 설정

                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
                        return configuration;
                    }
                }))
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)

                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(
                        authorize ->
                        authorize
                                .requestMatchers("/**").permitAll()
//                                .requestMatchers("/api/user/regist").permitAll() // 회원가입 전에는 허용
//                                .requestMatchers("/api/user/login").permitAll() // 로그인 전에는 허용
//                                .requestMatchers("/api/customer/**").permitAll() // customer로 로그인 한 경우에만 허용
//                                .anyRequest().permitAll()
//                                .authenticated()

                )
                .logout(logout ->
                                logout // 참고하면 좋은 블로그 : https://goto-pangyo.tistory.com/170
                                        .logoutUrl("/api/customer/logout") // 로그아웃 요청을 처리할 URL 설정
                                        .logoutSuccessUrl("/") //로그아웃 성공시 리다이렉트할 URL 설정
                                        .addLogoutHandler((request, response, authentication) -> { // 로그아웃 시도시 수행되는 핸들러
                                            HttpSession session = request.getSession();
                                            session.invalidate();
                                        })
                                        .logoutSuccessHandler((request, response, authentication) -> { // 로그아웃 성공시 수행되는 핸들러
                                            response.setStatus(HttpServletResponse.SC_OK);
                                            response.sendRedirect("/");
                                        })
                                        .invalidateHttpSession(true)
                                        .permitAll()
//                            .deleteCookies("JSESSIONID") // 로그아웃시 삭제할 쿠키 이름을 지정
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);




            return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
