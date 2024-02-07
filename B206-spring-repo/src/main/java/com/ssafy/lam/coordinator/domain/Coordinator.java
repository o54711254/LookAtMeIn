package com.ssafy.lam.coordinator.domain;

import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.hospital.domain.Hospital;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "coord_info")
public class Coordinator {
    @Id
    @GeneratedValue
    @Column(name="coord_info_seq")
    private Long coordSeq;

    @OneToOne
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hos_info_seq")
    private Hospital hospital;

    @Builder

    public Coordinator(Long coordSeq, User user, Hospital hospital) {
        this.coordSeq = coordSeq;
        this.user = user;
        this.hospital = hospital;
    }
}