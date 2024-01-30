package com.ssafy.lam.customer.model.service;

import com.ssafy.lam.entity.Customer;
import com.ssafy.lam.entity.User;
import com.ssafy.lam.user.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class CustomerServiceImpl implements CustomerService{

    private final UserService userService;

    @Autowired
    public CustomerServiceImpl(UserService userService) {
        this.userService = userService;
    }

    public User createCustomer(User customer){
        List<String> roles = new ArrayList<>();
        roles.add("CUSTOMER");
//        customer = (Customer) customer.toEntity(customer.getSeq(), customer.getUserId(), customer.getPassword(), roles);
        customer.setRoles(roles);
        return userService.createUser(customer);
    }

}
