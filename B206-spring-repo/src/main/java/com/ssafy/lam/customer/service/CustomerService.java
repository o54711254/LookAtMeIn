package com.ssafy.lam.customer.service;

import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.customer.dto.CustomerDto;
import com.ssafy.lam.entity.TokenInfo;
import com.ssafy.lam.user.domain.User;

public interface CustomerService {
    //    Customer getCustomer(long seq);
    com.ssafy.lam.customer.domain.Customer createCustomer(CustomerDto customerDto);
//    Customer updateCustomer(long seq, CustomerDto updatedCustomer);
//    void deleteCustomer(long seq);
//
    TokenInfo getLoginToken(User user);

//
    com.ssafy.lam.customer.domain.Customer findByCustomerId(String customerId);

    //}
    CustomerDto getCustomer(long userId);

    Customer updateCustomer(long userSeq, CustomerDto customerDto);
}