package com.ssafy.lam.customer.controller;


import com.ssafy.lam.customer.model.service.CustomerService;
import com.ssafy.lam.customer.model.service.CustomerServiceImpl;
import com.ssafy.lam.entity.Customer;
import com.ssafy.lam.user.controller.UserController;
import com.ssafy.lam.user.model.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
public class CustomerController{

    private final UserController userController;

    @Autowired
    public CustomerController(UserController userController) {
        log.info("CustomerController");
        this.userController = userController;
    }

    private Logger log = LoggerFactory.getLogger(CustomerController.class);

    @GetMapping("/regist")
    @Operation(summary = "고객 회원가입")
    public ResponseEntity<Void> createCustomer(@RequestBody Customer customer) {

        log.info("login userId : {}", customer.getUserId());
        return userController.createUser(customer);
    }


}
