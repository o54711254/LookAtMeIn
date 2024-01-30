package com.ssafy.lam.customer;

import com.ssafy.lam.customer.model.repository.CustomerRepository;
import com.ssafy.lam.entity.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;


    @Test
    @DisplayName("CustomerRepository test")
    public void CustomerRepositoryTest(){
        Customer customer = new Customer();
        customer.setUserId("polysd33a");
        customer.setPassword("323");

        customerRepository.save(customer);

    }
}
