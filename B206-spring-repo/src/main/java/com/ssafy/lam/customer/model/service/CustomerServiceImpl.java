package com.ssafy.lam.customer.model.service;

import com.ssafy.lam.customer.controller.CustomerController;
import com.ssafy.lam.customer.model.repository.CustomerRepository;
import com.ssafy.lam.entity.Customer;
import com.ssafy.lam.entity.TokenInfo;
import com.ssafy.lam.entity.User;
import com.ssafy.lam.user.model.service.UserService;
import com.ssafy.lam.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    protected final AuthenticationManagerBuilder authenticationManagerBuilder;

    protected final JwtTokenProvider jwtTokenProvider;
    private Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider jwtTokenProvider) {
        log.info("CustomerServiceImpl init");
        this.customerRepository = customerRepository;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Customer getCustomer(long seq) {
        return customerRepository.findById(seq).orElse(null);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        log.info("createCustomer customer :{}" , customer);
        if(customerRepository.existsById(customer.getSeq()))
            throw new RuntimeException("이미 존재하는 고객입니다.");
//        customer = (Customer) customer.toEntity(customer.getSeq(), customer.getUserId(), customer.getPassword(), customer.getRoles());
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(long seq, Customer updatedCustomer) {
        return null;
    }

    @Override
    public void deleteCustomer(long seq) {

    }

    @Override
    public TokenInfo getLoginToken(Customer customer) {
        TokenInfo tokenInfo = null;
        try{
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(customer.getUserId(), customer.getPassword());
            System.out.println("customer instanceof UserDetails = " + (customer instanceof UserDetails));
            System.out.println("authenticationToken = " + authenticationToken);
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            System.out.println("authentication = " + authentication);
            tokenInfo = jwtTokenProvider.generateToken(authentication);
            return tokenInfo;
        }catch(AuthenticationException e){
            System.out.println("AuthenticationException");
            System.out.println("e.getMessage() = " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }

    @Override
    public Customer findByUserId(String customerId) {
        return customerRepository.findByUserId(customerId).orElse(null);
    }

}
