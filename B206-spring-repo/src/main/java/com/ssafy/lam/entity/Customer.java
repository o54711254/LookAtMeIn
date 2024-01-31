package com.ssafy.lam.entity;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity

public class Customer extends User{
    public Customer() {
    }

    public Customer(long seq, String userId, String username, String password, List<String> roles) {
        super(seq, userId, username, password, roles);
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
