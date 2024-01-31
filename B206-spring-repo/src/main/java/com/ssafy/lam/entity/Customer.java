package com.ssafy.lam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity

public class Customer extends User{

    @Column(name = "customer_info_sex")
    private String sex;

    @Column(name = "customer_info_birth")
    private String birth;

    @Column(name = "customer_info_tel")
    private String tel;

    @Column(name = "customer_info_email")
    private String email;

    @Column(name = "customer_info_address")
    private String address;

    @Column(name = "customer_info_report")
    private int reportCnt;


    public Customer() {
    }

    public Customer(long seq, String userId, String username, String password, List<String> roles) {
        super(seq, userId, username, password, roles);
    }

    @Override
    public User toEntity(long seq, String userId, String username, String password, List<String> roles) {
        return super.toEntity(seq, userId, username, password, roles);
    }
}
