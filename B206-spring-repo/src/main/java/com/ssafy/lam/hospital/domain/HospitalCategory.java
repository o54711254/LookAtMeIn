package com.ssafy.lam.hospital.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class HospitalCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_seq")
    private Long categorySeq;

    @Column(name="part")
    private String part;

    @ManyToOne
    @JoinColumn(name="hos_info_seq")
    private Hospital hospital;
    @Builder
    public HospitalCategory(Long categorySeq, String part, Hospital hospital) {
        this.categorySeq = categorySeq;
        this.part = part;
        this.hospital = hospital;
    }

//    @ManyToOne
//    private Doctor doctor;
//
//    @Builder
//    public Category(Long categorySeq, String part, Doctor doctor) {
//        this.categorySeq = categorySeq;
//        this.part = part;
//        this.doctor = doctor;
//    }
}
