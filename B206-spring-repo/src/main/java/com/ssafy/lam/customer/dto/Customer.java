package com.ssafy.lam.customer.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
//@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;
    private String id;
    private String password;
    private String token;

    @Builder
    public Customer(int seq, String id, String password, String token) {
        this.seq = seq;
        this.id = id;
        this.password = password;
        this.token = token;
    }

    public Customer toEntity(int seq, String id, String password, String token) {
        return Customer.builder()
                .seq(seq)
                .id(id)
                .password(password)
                .token(token)
                .build();
    }

//    public Customer toEntity() {
//        return Customer.builder()
//                .seq(seq)
//                .id(id)
//                .password(password)
//                .token(token)
//                .build();
//    }

}