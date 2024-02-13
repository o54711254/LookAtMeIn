package com.ssafy.lam.reserve.domain;

import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "reserve")
public class Reserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reserveSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_user_seq")
    private User customer; // 예약한 고객

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_user_seq")
    private User hospital; // 예약을 받는 병원


    private int year;
    private int month;
    private int day;
    private String dayofweek;
    private int time;

    @Builder

    public Reserve(Long reserveSeq, User customer, User hospital, int year, int month, int day, String dayofweek, int time) {
        this.reserveSeq = reserveSeq;
        this.customer = customer;
        this.hospital = hospital;
        this.year = year;
        this.month = month;
        this.day = day;
        this.dayofweek = dayofweek;
        this.time = time;
    }
}