package com.ssafy.lam.customer;


import com.ssafy.lam.customer.controller.CustomerController;
import com.ssafy.lam.customer.dto.CustomerDto;
import com.ssafy.lam.customer.dto.CustomerTokenInfo;
import com.ssafy.lam.customer.model.service.CustomerService;
import com.ssafy.lam.entity.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
//@Transactional(readOnly = true)
public class CustomerLoginTest {
    @Autowired
    private CustomerController customerController;

    @Autowired
    private CustomerService customerService;


    @Test
    @DisplayName("회원가입 테스트")
//    @Transactional
    public void registTest(){
//        CustomerDto customerDto = CustomerDto
//                .builder()
//                .customerName("kimheesu")
//                .userId("polya")
//                .userPassword("1234")
//                .build();
//
//        Customer registCustomer = customerController.regist(customerDto);
//        System.out.println("registCustomer = " + registCustomer);
////        CustomerTokenInfo tokenInfo = customerController.login(customerDto);
//        System.out.println("tokenInfo = " + tokenInfo);

    }

}
