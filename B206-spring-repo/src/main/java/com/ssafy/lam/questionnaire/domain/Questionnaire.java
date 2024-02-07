package com.ssafy.lam.questionnaire.domain;

import com.ssafy.lam.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Questionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    private String blood;
    private String remark;
    private String title;
    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_seq")
    User customer;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="hospital_seq")
    User hospital;
}
