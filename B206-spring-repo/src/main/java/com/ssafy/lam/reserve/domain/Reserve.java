package com.ssafy.lam.reserve.domain;

import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.questionnaire.domain.Questionnaire;
import com.ssafy.lam.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "reserve")
public class Reserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserve_seq")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_user_seq")
    private User customer; // 예약한 고객

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_user_seq")
    private User hospital; // 예약을 받는 병원

    @OneToOne(mappedBy = "reserve", fetch = FetchType.LAZY)
    @JoinColumn(name = "questionnaire_seq")
    private Questionnaire questionnaire;

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
        this.questionnaire.setReserve(this);
    }


    @Column(name = "content")
    private String content;

    @Column(name = "price")
    private int price;

    private int year;
    private int month;
    private int day;
    private String dayofweek;
    private int time;

    private boolean deleted;
    private boolean completed;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "before_img_seq")
    private UploadFile beforeImg;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "after_img_seq")
    private UploadFile afterImg;


    @Builder
    public Reserve(Long seq, User customer, User hospital, Questionnaire questionnaire, String content, int price, int year, int month, int day, String dayofweek, int time, boolean deleted, boolean completed, UploadFile beforeImg, UploadFile afterImg) {
        this.seq = seq;
        this.customer = customer;
        this.hospital = hospital;
        this.questionnaire = questionnaire;
        this.content = content;
        this.price = price;
        this.year = year;
        this.month = month;
        this.day = day;
        this.dayofweek = dayofweek;
        this.time = time;
        this.deleted = deleted;
        this.completed = completed;
        this.beforeImg = beforeImg;
        this.afterImg = afterImg;
    }
}