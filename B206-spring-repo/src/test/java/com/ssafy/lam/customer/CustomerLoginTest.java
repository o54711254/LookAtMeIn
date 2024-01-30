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

//
    @Test
    @DisplayName("회원가입 테스트")
    @Transactional
    public void registTest(){
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_CUSTOMER");
        Customer customer = new Customer(2L,"test", "test", roles);
        ResponseEntity<Void> responseEntity = customerController.createCustomer(customer);
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

}
