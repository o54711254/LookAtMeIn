package com.ssafy.lam.user.domain;

import com.ssafy.lam.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "coord_info")
public class CoordInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coordInfoSeq;

    private String coordInfoName;

    @ManyToOne
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne
    @JoinColumn(name = "hos_info_seq")
    private HosInfo hosInfo;

    @Builder
    public CoordInfo(String coordInfoName, User user, HosInfo hosInfo) {
        this.coordInfoName = coordInfoName;
        this.user = user;
        this.hosInfo = hosInfo;
    }
}