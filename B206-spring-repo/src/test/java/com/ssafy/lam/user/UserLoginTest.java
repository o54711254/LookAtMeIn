package com.ssafy.lam.user;

import com.ssafy.lam.user.controller.UserController;
import com.ssafy.lam.user.model.service.UserService;
import com.ssafy.lam.entity.User;
import com.ssafy.lam.entity.TokenInfo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 참고 블로그
 * https://suddiyo.tistory.com/entry/Spring-Spring-Security-JWT-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0-4
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class UserLoginTest {

    
    @Autowired
    private UserService userService;
    @Test
    @DisplayName("로그인 테스트")
    @Transactional
    public void loginTest() throws Exception{
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        User user = User.builder()
                .userId("test")
                .password("test")
                .roles(roles)
                .build();

        userService.createUser(user);

        TokenInfo tokenInfo = userService.getLoginToken(user);
        System.out.println("tokenInfo = " + tokenInfo);

    }
}
