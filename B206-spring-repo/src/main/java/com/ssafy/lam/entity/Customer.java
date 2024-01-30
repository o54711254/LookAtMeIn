package com.ssafy.lam.entity;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.List;

//@RequiredArgsConstructor
@Entity
@NoArgsConstructor
public class Customer extends User{

    public Customer(long seq, String userId, String password, List<String> roles) {
        super(seq, userId, password, roles);
    }


//    public Customer toEntity(long seq, String userId, String password, List<String> roles) {
//        return (Customer)super.builder()
//                                .seq(seq)
//                                .userId(userId)
//                                .password(password)
//                                .roles(roles)
//                                .build();
//    }
}
