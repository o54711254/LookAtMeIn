package com.ssafy.lam.customer.model.service;

import com.ssafy.lam.customer.dto.CustomerDto;
import com.ssafy.lam.entity.Customer;
import com.ssafy.lam.entity.TokenInfo;
import com.ssafy.lam.entity.User;

import java.util.Optional;

public interface CustomerService {
    //    Customer getCustomer(long seq);
    Customer createCustomer(CustomerDto customerDto);
//    Customer updateCustomer(long seq, CustomerDto updatedCustomer);
//    void deleteCustomer(long seq);
//
    TokenInfo getLoginToken(User user);

//
    Customer findByCustomerId(String customerId);
//}
}