package com.ssafy.lam.customer;


import com.ssafy.lam.customer.controller.CustomerController;
import com.ssafy.lam.entity.Customer;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
@Transactional
public class CustomerLoginTest {
    @Autowired
    private CustomerController customerController;
    private Customer customer;

    @BeforeEach
    void beforeEacrh(){
        customer = (Customer)Customer.builder()
                                    .seq(1L)
                                    .userId("test")
                                    .password("test")
                                    .build();
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void registTest(){
        ResponseEntity<Void> responsEntity = customerController.createCustomer(customer);
        Assertions.assertThat(responsEntity.getStatusCodeValue()).isEqualTo(200);

    }

}
