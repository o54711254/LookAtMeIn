package com.ssafy.lam.hospital.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@ToString
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_seq")
    private Long categorySeq;

    @Column(name="part")
    private String part;

    @ManyToOne
    private Doctor doctor;

    @Builder
    public Category(Long categorySeq, String part, Doctor doctor) {
        this.categorySeq = categorySeq;
        this.part = part;
        this.doctor = doctor;
    }
}
