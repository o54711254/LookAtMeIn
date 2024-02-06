package com.ssafy.lam.hospital.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue
    @Column(name="category_seq")
    private Long categorySeq;

    @Column(name="part")
    private String part;

    @ManyToOne
    @JoinColumn(name="hos_info_seq")
    private Hospital hospital;

    @Builder
    public Category(Long categorySeq, String part, Hospital hospital) {
        this.categorySeq = categorySeq;
        this.part = part;
        this.hospital = hospital;
    }
}
