package com.ssafy.lam.entity;

import java.util.List;

//@RequiredArgsConstructor
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
