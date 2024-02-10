package com.ssafy.lam.reserve.domain;

import com.ssafy.lam.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "pastReserve")
public class PastReserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "past_reserve_seq")
    private Long pReserveSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_user_seq")
    private User customer; // 예약한 고객

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_user_seq")
    private User hospital; // 예약을 받는 병원

    @Column(name = "past_reserve_content")
    private String pContent;

    @Column(name = "past_reserve_price")
    private Integer pPrice;

    private Integer year;
    private Integer month;
    private Integer day;
    private String dayofweek;
    private Integer time;

    private Integer img;

    @Builder
    public PastReserve(User customer, User hospital, String pContent, int pPrice, int year, int month, int day, String dayofweek, int time, int img) {
        this.customer = customer;
        this.hospital = hospital;
        this.pContent = pContent;
        this.pPrice = pPrice;
        this.year = year;
        this.month = month;
        this.day = day;
        this.dayofweek = dayofweek;
        this.time = time;
        this.img = img;
    }
}
