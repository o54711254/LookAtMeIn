package com.ssafy.lam.mypage.controller;

import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.customer.dto.CustomerDto;
import com.ssafy.lam.customer.service.CustomerService;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.dto.HospitalDto;
import com.ssafy.lam.hospital.service.HospitalService;
import com.ssafy.lam.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mypage")
public class MypageController {

    private final UserService userService;
    private final CustomerService customerService;
    private final HospitalService hospitalService;

    @GetMapping("/{userSeq}")
    public ResponseEntity<?> getMypageByUser(@PathVariable long userSeq) {
        String role = userService.getUser(userSeq).getUserType();

        if(role.equals("CUSTOMER")) {
            CustomerDto dto = customerService.getCustomer(userSeq);
            if (dto != null) {
                return new ResponseEntity<CustomerDto>(dto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }else {
            HospitalDto dto = hospitalService.getHospital(userSeq);
            if (dto != null) {
                return new ResponseEntity<HospitalDto>(dto, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
    }

    @PutMapping("/user/{userSeq}")
    public ResponseEntity<?> updateCustomer(@PathVariable long userSeq, @RequestBody CustomerDto customerDto) {
        Customer customerSaveDto = customerService.updateCustomer(userSeq,customerDto);
        return new ResponseEntity<Customer>(customerSaveDto, HttpStatus.OK);
    }

    @PutMapping("/hospital/{userSeq}")
    public ResponseEntity<?> updateHospital(@PathVariable long userSeq, @RequestBody HospitalDto hospitalDto) {
        Hospital hospitalSaveDto = hospitalService.updateHospital(userSeq,hospitalDto);
        return new ResponseEntity<Hospital>(hospitalSaveDto, HttpStatus.OK);
    }

//    @GetMapping("/")
//    public ResponseEntity<?> getMapageFreeboard(){
//
//    }
}
