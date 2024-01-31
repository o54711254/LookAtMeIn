package com.ssafy.lam.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "customer_info")
public class CustomerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerInfoSeq;

    private String customerInfoName;

    @ManyToOne
    @JoinColumn(name = "user_seq")
    private User user;

    @Builder
    public CustomerInfo(String customerInfoName, User user) {
        this.customerInfoName = customerInfoName;
        this.user = user;
    }
}