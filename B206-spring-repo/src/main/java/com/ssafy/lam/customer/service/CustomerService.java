package com.ssafy.lam.customer.service;

import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.customer.dto.CustomerDto;
import com.ssafy.lam.entity.TokenInfo;
import com.ssafy.lam.user.domain.User;
import org.springframework.web.multipart.MultipartFile;

public interface CustomerService {
    //    Customer getCustomer(long seq);
    Customer createCustomer(CustomerDto customerDto);

    CustomerDto getCustomer(Long userSeq);

    Customer updateCustomer(Long userSeq, CustomerDto updatedCustomer, MultipartFile profile);
    Customer findByCustomerId(String customerId);
//}
}