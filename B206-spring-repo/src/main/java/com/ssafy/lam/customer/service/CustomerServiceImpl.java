package com.ssafy.lam.customer.service;

import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.customer.domain.CustomerRepository;
import com.ssafy.lam.customer.dto.CustomerDto;
import com.ssafy.lam.entity.TokenInfo;
import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.user.domain.UserRepository;
import com.ssafy.lam.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    private Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);


//    @Override
//    public Customer getCustomer(long seq) {
//        return customerRepository.findById(seq).orElse(null);
//    }

    @Override
    public Customer createCustomer(CustomerDto customerDto) {
        log.info("createCustomer customer :{}", customerDto);
        if (customerRepository.existsByUserUserId(customerDto.getUserId()))
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
        Customer customer = com.ssafy.lam.customer.domain.Customer.builder()
                .user(user)
                .gender(customerDto.getCustomerGender())
                .tel(customerDto.getCustomerPhoneNumber())
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
    public TokenInfo getLoginToken(User user) {
        try {
            return userService.getLoginToken(user);
        } catch (AuthenticationException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        } catch (RuntimeException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    //
    @Override
    public com.ssafy.lam.customer.domain.Customer findByCustomerId(String customerId) {
        return customerRepository.findByUserUserId(customerId).orElse(null);
    }

    public CustomerDto getCustomer(long userId) {
        Optional<com.ssafy.lam.customer.domain.Customer> customerOptional = customerRepository.findByUserUserSeq(userId);
        if (customerOptional.isPresent()) {
            com.ssafy.lam.customer.domain.Customer customer = customerOptional.get();

            CustomerDto dto = CustomerDto.builder()
                    .userId(customer.getUser().getUserId())
                    .userPassword(customer.getUser().getPassword())
                    .customerName(customer.getUser().getName())
                    .customerGender(customer.getGender())
                    .customerPhoneNumber(customer.getTel())
                    .customerEmail(customer.getEmail())
                    .customerAddress(customer.getAddress())
                    .build();
            return dto;
        } else {
            return null;
        }
    }

    public Customer updateCustomer(long userSeq, CustomerDto customerDto) {
        User user = userService.getUser(userSeq);
        Customer customer = customerRepository.findByUserUserSeq(userSeq).get();

        user.setName(customerDto.getCustomerName());
        user.setPassword(customerDto.getCustomerName());
        customer.setTel(customerDto.getCustomerPhoneNumber());
        customer.setEmail(customerDto.getCustomerEmail());
        customer.setAddress(customerDto.getCustomerAddress());
        userRepository.save(user);

        return customerRepository.save(customer);
    }

}
