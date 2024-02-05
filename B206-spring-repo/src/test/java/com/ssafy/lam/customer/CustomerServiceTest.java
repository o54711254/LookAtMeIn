package com.ssafy.lam.customer;

import com.ssafy.lam.customer.dto.CustomerDto;
import com.ssafy.lam.customer.service.CustomerService;
import com.ssafy.lam.customer.domain.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

    @Test
    @DisplayName("updateCustomer 테스트")
    @Transactional
    public void updateCustomerTest(){


        CustomerDto updatedCustomerDto = CustomerDto.builder()
                .userId("ssafy")
                .userPassword("1234")
                .customerName("kimheesu")
                .build();
        Customer updatedCustomer = customerService.updateCustomer(1L, updatedCustomerDto);
        System.out.println("updatedCustomer = " + updatedCustomer);

    }


}
