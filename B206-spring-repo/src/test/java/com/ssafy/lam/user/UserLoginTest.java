package com.ssafy.lam.user;

import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.customer.dto.CustomerDto;
import com.ssafy.lam.customer.service.CustomerService;
import com.ssafy.lam.user.controller.UserController;
import com.ssafy.lam.user.dto.UserDto;
import com.ssafy.lam.user.dto.UserTokenInfo;
import com.ssafy.lam.user.service.UserService;
import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.entity.TokenInfo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserController userController;
    @Test
    @DisplayName("로그인 테스트")
    @Transactional
    public void loginTest() throws Exception{
        CustomerDto customerDto = CustomerDto.builder()
                .userId("Asdads")
                .customerName("test")
                .userPassword("test")
                .customerGender("test")
                .customerEmail("test")
                .customerAddress("test")
                .build();


        customerService.createCustomer(customerDto);
        UserDto userDto = UserDto.builder()
                .userId("Asdads")
                .userPassword("test")
                .build();


//        UserTokenInfo userTokenInfo = userController.login(userDto);
//        System.out.println("userTokenInfo = " + userTokenInfo);
    }
}
