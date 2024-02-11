package com.ssafy.lam.requestboard.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Surgery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "surgery_seq")
    private Long seq;

    @Column(name = "surgery_part")
    private String part;

    @Column(name = "surgery_type")
    private int type;

    private LocalDate regDate;

    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    private Requestboard requestboard;

    @Builder
    public Surgery(Long seq, String part, int type, LocalDate regDate, boolean isDeleted, Requestboard requestboard) {
        this.seq = seq;
        this.part = part;
        this.type = type;
        this.regDate = regDate;
        this.isDeleted = isDeleted;
        this.requestboard = requestboard; // 생성자에 추가
    }
}
