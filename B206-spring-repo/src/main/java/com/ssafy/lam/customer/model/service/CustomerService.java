package com.ssafy.lam.customer.model.service;

import com.ssafy.lam.entity.Customer;
import com.ssafy.lam.entity.TokenInfo;
import com.ssafy.lam.entity.User;

public interface CustomerService {
    Customer getCustomer(long seq);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(long seq, Customer updatedCustomer);
    void deleteCustomer(long seq);

    TokenInfo getLoginToken(Customer customer);

    Customer findByCustomerId(String customerId);
}
