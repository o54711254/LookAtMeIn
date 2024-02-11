package com.ssafy.lam.hospital.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "doc_info")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long docInfoSeq;

    private String docInfoName;

    @ManyToOne
    @JoinColumn(name = "hos_info_seq")
    private Hospital hospital;

    @Builder
    public Doctor(Long docInfoSeq, String docInfoName, Hospital hospital) {
        this.docInfoSeq = docInfoSeq;
        this.docInfoName = docInfoName;
        this.hospital = hospital;
    }
}