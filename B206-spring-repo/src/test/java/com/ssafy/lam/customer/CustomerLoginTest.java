package com.ssafy.lam.customer;


import com.ssafy.lam.customer.controller.CustomerController;
import com.ssafy.lam.customer.model.service.CustomerService;
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

    @Autowired
    private CustomerService customerService;


    @Test
    @DisplayName("회원가입 테스트")
    @Transactional
    public void registTest(){//
        Customer customer = new Customer(2L, "test", "kimheesu", "passwd", null);
        System.out.println("customer = " + customer);
        Customer createCustomer = customerService.createCustomer(customer);
        System.out.println("createCustomer = " + createCustomer);

        Assertions.assertThat(createCustomer.getUsername()).isEqualTo(customer.getUsername());
    }

}
