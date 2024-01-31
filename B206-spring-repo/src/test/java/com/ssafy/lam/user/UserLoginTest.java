package com.ssafy.lam.user;

import com.ssafy.lam.user.controller.UserController;
import com.ssafy.lam.user.service.UserService;
import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.user.domain.TokenInfo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private UserController userController;
    @Autowired
    private TestRestTemplate testRestTemplate;


    private User user;

    @LocalServerPort
    int randomServerPort;

    @BeforeEach
    void beforeEach() {
//        System.out.println("beforeEach");
        
        user = User.builder()
                .seq(1L)
                .userId("test")
                .password("test")
                
                .build();
            
    }

    @Test
    @DisplayName("로그인 테스트")
    public void loginTest() throws Exception{
        
        userService.createUser(user);
        TokenInfo tokenInfo = userService.login(user);
        System.out.println("tokenInfo = " + tokenInfo);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenInfo.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

//
////        log.info("headers = {}", headers);
        System.out.println("headers = " + headers);
//
        String url = "http://localhost:"+randomServerPort+"/api/customer/login";

        HttpEntity<User> request = new HttpEntity<>(user, headers);

        ResponseEntity<TokenInfo> responseEntity = testRestTemplate.postForEntity(url, request, TokenInfo.class);

    }
}
