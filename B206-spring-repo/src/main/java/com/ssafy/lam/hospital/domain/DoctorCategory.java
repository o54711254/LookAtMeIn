package com.ssafy.lam.hospital.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor
@ToString
public class DoctorCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_seq")
    private Long categorySeq;

    @Column(name="part")
    private String part;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="doc_seq")
    private Doctor doctor;

    @Builder
    public DoctorCategory(Long categorySeq, String part, Doctor doctor) {
        this.categorySeq = categorySeq;
        this.part = part;
        this.doctor = doctor;
    }
}
