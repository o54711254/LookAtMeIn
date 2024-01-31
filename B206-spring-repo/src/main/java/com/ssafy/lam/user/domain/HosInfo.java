package com.ssafy.lam.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "hos_info")
public class HosInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hosInfoSeq;

    @ManyToOne
    @JoinColumn(name = "user_seq")
    private User user;

    @Builder
    public HosInfo(User user) {
        this.user = user;
    }
}