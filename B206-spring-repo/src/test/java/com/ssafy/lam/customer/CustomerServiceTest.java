package com.ssafy.lam.customer;

import com.ssafy.lam.customer.controller.CustomerController;
import com.ssafy.lam.customer.dto.CustomerDto;
import com.ssafy.lam.customer.model.service.CustomerService;
import com.ssafy.lam.entity.Customer;
import com.ssafy.lam.entity.TokenInfo;
import com.ssafy.lam.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    @DisplayName("createCustomer 테스트")
    @Transactional
    public void createCustomerTest(){

        CustomerDto customerDto = CustomerDto.builder()
                .userId("ssafy")
                .userPassword("1234")
                .customerName("kimheesu")
                .build();
        Customer customer = customerService.createCustomer(customerDto);
        System.out.println("customer = " + customer);

    }


}
