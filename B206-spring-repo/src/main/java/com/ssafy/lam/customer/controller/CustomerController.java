package com.ssafy.lam.customer.controller;


import com.ssafy.lam.customer.dto.CustomerTokenInfo;
import com.ssafy.lam.customer.model.service.CustomerService;
import com.ssafy.lam.entity.Customer;
import com.ssafy.lam.entity.TokenInfo;
import com.ssafy.lam.entity.User;
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
public class CustomerController{
    private Logger log = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;


    @Autowired
    public CustomerController(CustomerService customerService) {
        log.info("CustomerController init");
        this.customerService = customerService;
    }

    @PostMapping("/regist")
    @Operation(summary = "고객 회원가입")
    public ResponseEntity<Void> createCustomer(@RequestBody Customer customer) {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_CUSTOMER");
        customer.setRoles(roles);
        log.info("createCustomer customer : {}", customer);

        customerService.createCustomer(customer);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    @Operation(summary = "고객 로그인")
    public ResponseEntity<CustomerTokenInfo> loginCustomer(@RequestBody Customer customer) {
        log.info("loginCustomer customer : {}", customer);
        Customer customer1 = customerService.findByCustomerId(customer.getUserId());
        if(customer1 == null){
            return ResponseEntity.notFound().build();
        }
        if(!customer1.getPassword().equals(customer.getPassword())){
            return ResponseEntity.notFound().build();
        }

//        String user_type = customerService.getUserType(customer1);
        TokenInfo tokenInfo = customerService.getLoginToken(customer1);
        log.info("tokenInfo : {}", tokenInfo);
        CustomerTokenInfo customerTokenInfo = CustomerTokenInfo.builder()
                .tokenInfo(tokenInfo)
//                .user_type(user_type)
                .userId(customer1.getUserId())
                .build();

        return ResponseEntity.ok().body(customerTokenInfo);
    }


//  private final CustomerService customerService;
//
//  @Autowired
//    public CustomerController(CustomerService customerService) {
//        this.customerService = customerService;
//    }
//
//    @PostMapping("/regist")
//    @Operation(summary = "고객 회원가입")
//    public ResponseEntity<Void> createCustomer(@RequestBody Customer customer) {
//
//        log.info("createCustomer customer : {}", customer);
//        customerService.createCustomer(customer);
//        return ResponseEntity.ok().build();
//    }
}
