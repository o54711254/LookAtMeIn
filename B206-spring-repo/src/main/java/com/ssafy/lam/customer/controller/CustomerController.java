package com.ssafy.lam.customer.controller;


import com.ssafy.lam.customer.dto.CustomerTokenInfo;
import com.ssafy.lam.customer.model.service.CustomerService;
import com.ssafy.lam.entity.Customer;
import com.ssafy.lam.entity.TokenInfo;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;
    private Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    public CustomerController(CustomerService customerService) {
        log.info("CustomerController init");
        this.customerService = customerService;
    }

    @GetMapping("/test")
    public String getTest() {
        return "get Success";
    }

    @PostMapping("/postTest")
    public String postTest() {
        return "post Success";
    }

    @PostMapping("/regist")
    @Operation(summary = "고객 회원가입")
    public ResponseEntity<Void> createCustomer(@RequestBody Customer customer) {
        log.info("createCustomer customer : {}", customer);
        List<String> roles = new ArrayList<>();
        roles.add("CUSTOMER");
        customer.setRoles(roles);

        customerService.createCustomer(customer);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    @Operation(summary = "고객 로그인")
    public ResponseEntity<CustomerTokenInfo> loginCustomer(@RequestBody Customer customer) {

        TokenInfo tokenInfo = customerService.getLoginToken(customer);

        Customer customer1 = customerService.findByUserId(customer.getUserId());
        log.info("customer1: {}", customer1);
        if(customer1 == null){
            return ResponseEntity.notFound().build();
        }

        if (!customer1.getPassword().equals(customer.getPassword())) {
            return ResponseEntity.notFound().build();
        }

        CustomerTokenInfo customerTokenInfo = CustomerTokenInfo.builder()
                .userId(customer.getUserId())
                .username(customer.getName())
                .tokenInfo(tokenInfo)
                .build();

        return ResponseEntity.ok(customerTokenInfo);
    }


}
