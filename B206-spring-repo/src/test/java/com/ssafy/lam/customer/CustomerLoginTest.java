package com.ssafy.lam.customer;


import com.ssafy.lam.customer.controller.CustomerController;
import com.ssafy.lam.entity.Customer;
import com.ssafy.lam.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional(readOnly = true)
public class CustomerLoginTest {
    @Autowired
    private CustomerController customerController;
    private User customer;

    @BeforeEach
    void beforeEacrh(){
        customer = User.builder()
                .seq(2L)
                .userId("test")
                .password("test")
                .build();

    }

    @Test
    @DisplayName("회원가입 테스트")
    @Transactional
    public void registTest(){
        ResponseEntity<Void> responsEntity = customerController.createCustomer(customer);
        Assertions.assertThat(responsEntity.getStatusCodeValue()).isEqualTo(200);

    }

}
