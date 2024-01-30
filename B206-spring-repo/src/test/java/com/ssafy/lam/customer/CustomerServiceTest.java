package com.ssafy.lam.customer;

import com.ssafy.lam.customer.model.repository.CustomerRepository;
import com.ssafy.lam.customer.model.service.CustomerService;
import com.ssafy.lam.entity.Customer;
import com.ssafy.lam.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Transactional(readOnly = true)
public class CustomerServiceTest {


//    private final CustomerRepository customerRepository;
//    protected final AuthenticationManagerBuilder authenticationManagerBuilder;
//    protected final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomerService customerService;



    @Test
    @DisplayName("createCustomer 테스트")
    @Transactional
    public void createCustomerTest(){
        List<String> roles = new ArrayList<>();
        roles.add("CUSTOMER");
        Customer customer = new Customer(2L,"test", "test", roles);
        Customer customer1 = customerService.createCustomer(customer);
        Assertions.assertThat(customer1.getSeq()).isEqualTo(customer.getSeq());

    }


}
