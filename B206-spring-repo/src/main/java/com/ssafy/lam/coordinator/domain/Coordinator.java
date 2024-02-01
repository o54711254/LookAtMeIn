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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coordInfoSeq;

    private String coordInfoName;

    @ManyToOne
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne
    @JoinColumn(name = "hos_info_seq")
    private Hospital hospital;

    @Builder
    public Coordinator(String coordInfoName, User user, Hospital hospital) {
        this.coordInfoName = coordInfoName;
        this.user = user;
        this.hospital = hospital;
    }
}