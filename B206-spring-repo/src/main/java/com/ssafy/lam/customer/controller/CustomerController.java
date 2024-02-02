package com.ssafy.lam.customer.controller;


import com.ssafy.lam.customer.dto.CustomerDto;
import com.ssafy.lam.customer.dto.CustomerTokenInfo;
import com.ssafy.lam.customer.service.CustomerService;
import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.entity.TokenInfo;
import com.ssafy.lam.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/regist")
    @Operation(summary = "고객 회원가입")
    public ResponseEntity<Void> regist(@RequestBody CustomerDto customerDto) {
        log.info("회원가입 정보 : {}", customerDto);
        customerService.createCustomer(customerDto);
        return ResponseEntity.ok().build();
    }




}
