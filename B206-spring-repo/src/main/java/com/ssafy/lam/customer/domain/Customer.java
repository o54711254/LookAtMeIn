package com.ssafy.lam.customer.domain;

import com.ssafy.lam.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor

@ToString
public class Customer{


    @Id
    @GeneratedValue
    private Long customerSeq;

    @OneToOne
    private User user;

    @Column(name = "customer_info_sex")
    private String gender;

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


    @Builder

    public Customer(Long customerSeq, User user, String gender, String birth, String tel, String email, String address, int reportCnt) {
        this.customerSeq = customerSeq;
        this.user = user;
        this.gender = gender;
        this.birth = birth;
        this.tel = tel;
        this.email = email;
        this.address = address;
        this.reportCnt = reportCnt;
    }
}
