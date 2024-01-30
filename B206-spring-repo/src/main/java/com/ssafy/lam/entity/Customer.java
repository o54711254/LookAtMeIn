package com.ssafy.lam.entity;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
public class Customer extends com.ssafy.lam.dto.User {


    public Customer toEntity(long seq, String userId, String password, List<String> roles) {
        return (Customer)super.builder()
                                .seq(seq)
                                .userId(userId)
                                .password(password)
                                .roles(roles)
                                .build();
    }
}
