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

    @PostMapping("/login")
    @Operation(summary = "고객 로그인")
    public ResponseEntity<CustomerTokenInfo> login(@RequestBody CustomerDto customerDto) {
        log.info("로그인 정보 : {}", customerDto);
        User user = User.builder()
                .userId(customerDto.getUserId())
                .name(customerDto.getCustomerName())
                .password(customerDto.getUserPassword())
                .build();

        log.info("로그인 User 정보: {}", user);

        Customer customer1 = customerService.findByCustomerId(user.getUserId());

        if(customer1 == null){
//            return ResponseEntity.notFound().build();
            return null;
        }

        if(!customer1.getUser().getPassword().equals(user.getPassword())){
//            return ResponseEntity.notFound().build();
            return null;
        }
        TokenInfo tokenInfo = customerService.getLoginToken(user);

        CustomerTokenInfo customerTokenInfo = CustomerTokenInfo.builder()
                .userId(customer1.getUser().getUserId())
                .username(customer1.getUser().getUsername())
                .tokenInfo(tokenInfo)
                .build();

        return ResponseEntity.ok(customerTokenInfo);





    }


}
