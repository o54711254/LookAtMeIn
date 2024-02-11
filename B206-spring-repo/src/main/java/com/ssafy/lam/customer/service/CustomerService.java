package com.ssafy.lam.customer.service;

import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.customer.dto.CustomerDto;
import com.ssafy.lam.entity.TokenInfo;
import com.ssafy.lam.user.domain.User;

public interface CustomerService {
    //    Customer getCustomer(long seq);
    Customer createCustomer(CustomerDto customerDto);

    CustomerDto getCustomer(Long userSeq);

    Customer updateCustomer(Long userSeq, CustomerDto updatedCustomer);
    Customer findByCustomerId(String customerId);
//}
}