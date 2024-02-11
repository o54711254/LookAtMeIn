package com.ssafy.lam.favorites.domain;

import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Favorites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorites_seq")
    private Long seq;

    @OneToOne
    @JoinColumn(name = "user_seq")
    private User user;

    @OneToOne
    @JoinColumn(name = "hos_info_seq")
    private Hospital hospital;

    @Column(name = "favorites_isLiked")
    private boolean isLiked;

    @Builder
    public Favorites(Long seq, User user, Hospital hospital, boolean isLiked) {
        this.seq = seq;
        this.user = user;
        this.hospital = hospital;
        this.isLiked = isLiked;
    }
}
