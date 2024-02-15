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

    @Column(name = "user_seq")
    private Long userSeq;

    @Column(name = "hos_info_seq")
    private Long hospitalSeq;

    @Column(name = "favorites_isLiked")
    private boolean isLiked;

    public void setLike(boolean like) { this.isLiked = like; }

    @Builder
    public Favorites(Long seq, Long userSeq, Long hospitalSeq, boolean isLiked) {
        this.seq = seq;
        this.userSeq = userSeq;
        this.hospitalSeq = hospitalSeq;
        this.isLiked = isLiked;
    }
}