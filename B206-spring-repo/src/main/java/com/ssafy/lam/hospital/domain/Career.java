package com.ssafy.lam.hospital.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "career")
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long careerSeq;

    private String careerStart;
    private String careerEnd;
    private String careerContent;

    @ManyToOne
    private Doctor doctor;

    @Builder
    public Career(Long careerSeq, String careerStart, String careerEnd, String careerContent, Doctor doctor) {
        this.careerSeq = careerSeq;
        this.careerStart = careerStart;
        this.careerEnd = careerEnd;
        this.careerContent = careerContent;
        this.doctor = doctor;
    }
}