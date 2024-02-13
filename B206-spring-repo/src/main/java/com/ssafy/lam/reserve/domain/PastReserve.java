package com.ssafy.lam.reserve.domain;

import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.questionnaire.domain.Questionnaire;
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
    @JoinColumn(name="customer_seq")
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="hospital_seq")
    private User hospital;


    @Column(name = "past_reserve_content")
    private String pContent;

    @Column(name = "past_reserve_price")
    private int pPrice;

    private int year;
    private int month;
    private int day;
    private String dayofweek;
    private int time;

//    private int img;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "before_img_seq")
    private UploadFile beforeImg;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "after_img_seq")
    private UploadFile afterImg;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionnaire_seq")
    private Questionnaire questionnaire;

    @Builder
    public PastReserve(Long pReserveSeq, User customer, User hospital, String pContent, int pPrice, int year, int month, int day, String dayofweek, int time, UploadFile beforeImg, UploadFile afterImg, Questionnaire questionnaire) {
        this.pReserveSeq = pReserveSeq;
        this.customer = customer;
        this.hospital = hospital;
        this.pContent = pContent;
        this.pPrice = pPrice;
        this.year = year;
        this.month = month;
        this.day = day;
        this.dayofweek = dayofweek;
        this.time = time;
        this.beforeImg = beforeImg;
        this.afterImg = afterImg;
        this.questionnaire = questionnaire;
    }
}
