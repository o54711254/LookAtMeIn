package com.ssafy.lam.customer.model.service;

import com.ssafy.lam.entity.Customer;
import com.ssafy.lam.entity.TokenInfo;
import com.ssafy.lam.entity.User;

import java.util.Optional;

public interface CustomerService {
    Customer getCustomer(long seq);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(long seq, Customer updatedCustomer);
    void deleteCustomer(long seq);

    TokenInfo getLoginToken(Customer customer);

    Customer findByUserId(String customerId);
}
