package com.ssafy.lam.customer.service;

import com.ssafy.lam.customer.dto.CustomerDto;
import com.ssafy.lam.customer.domain.CustomerRepository;
import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.entity.TokenInfo;
import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final UserService userService;

    private Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);


//    @Override
//    public Customer getCustomer(long seq) {
//        return customerRepository.findById(seq).orElse(null);
//    }

    @Override
    public Customer createCustomer(CustomerDto customerDto) {
        log.info("createCustomer customer :{}" , customerDto);
        if(customerRepository.existsByUserUserId(customerDto.getUserId()))
            throw new RuntimeException("이미 존재하는 고객입니다.");

        List<String> roles = new ArrayList<>();
        roles.add("CUSTOMER");

        User user = User.builder()
                .userId(customerDto.getUserId())
                .name(customerDto.getCustomerName())
                .password(customerDto.getUserPassword())
                .roles(roles)
                .userType("CUSTOMER")
                .build();
        userService.createUser(user);
        Customer customer = Customer.builder()
                .user(user)
                .gender(customerDto.getCustomerGender())
                .email(customerDto.getCustomerEmail())
                .address(customerDto.getCustomerAddress())
                .build();


        return customerRepository.save(customer);
    }

//    @Override
//    public Customer updateCustomer(long seq, Customer updatedCustomer) {
//        return null;
//    }

//    @Override
//    public void deleteCustomer(long seq) {
//
//    }

    @Override
    public Customer findByCustomerId(String customerId) {
        return customerRepository.findByUserUserId(customerId).orElse(null);
    }

}
