package com.ssafy.lam.customer.dto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
//@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
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