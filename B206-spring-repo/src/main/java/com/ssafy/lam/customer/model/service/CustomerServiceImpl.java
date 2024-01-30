package com.ssafy.lam.customer.model.service;

import com.ssafy.lam.dto.TokenInfo;
import com.ssafy.lam.dto.User;
import com.ssafy.lam.entity.Customer;
import com.ssafy.lam.user.model.repository.UserRepository;
import com.ssafy.lam.user.model.service.UserService;
import com.ssafy.lam.user.model.service.UserServiceImpl;
import com.ssafy.lam.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private UserService userService;

    public Customer createCustomer(Customer customer){
        List<String> roles = new ArrayList<>();
        roles.add("CUSTOMER");
        customer = customer.toEntity(customer.getSeq(), customer.getUserId(), customer.getPassword(), roles);
        return (Customer) userService.createUser(customer);
    }

}
