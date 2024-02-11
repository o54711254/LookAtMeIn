package com.ssafy.lam.customer.controller;


import com.ssafy.lam.customer.dto.CustomerDto;
import com.ssafy.lam.customer.service.CustomerService;
import com.ssafy.lam.customer.domain.Customer;
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


    @PutMapping("/mypage/modify/{userSeq}")
    @Operation(summary = "고객 정보 수정")
    public ResponseEntity<Void> modify(@PathVariable Long userSeq, @RequestBody CustomerDto customerDto) {
        log.info("수정 정보 : {}", customerDto);
        Customer customer = customerService.updateCustomer(userSeq, customerDto);
        return ResponseEntity.ok().build();
    }



}
