package com.ssafy.lam.hospital.dto;

import com.ssafy.lam.user.dto.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("2")
public class Hospital extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hos_info_seq")
    private Long seq;
    @Column(name = "hos_info_name")
    private String name;
    @Column(name = "hos_info_tel")
    private String tel;
    @Column(name = "hos_info_email")
    private String email;
    @Column(name = "hos_info_address")
    private String address;
    @Column(name = "hos_info_open")
    private String open;
    @Column(name = "hos_info_close")
    private String close;
    @Column(name = "hos_info_intro")
    private String intro;
    @Column(name = "hos_info_isapproved")
    private boolean isApproved;
    @Column(name = "hos_info_like")
    private int like;
    @Column(name = "hos_info_url")
    private String url;

    @OneToOne
    @JoinColumn(name = "member_seq")
    private User user;

    @Builder
    public Hospital(Long seq, String name, String tel, String email, String address, String open, String close,
                    String intro, boolean isApproved, int like, String url, User user) {
        this.seq = seq;
        this.name = name;
        this.tel = tel;
        this.email = email;
        this.address = address;
        this.open = open;
        this.close = close;
        this.intro = intro;
        this.isApproved = isApproved;
        this.like = like;
        this.url = url;
        this.user = user;
    }

    public Hospital toEntity(Long seq, String name, String tel, String email, String address, String open, String close,
                             String intro, boolean isApproved, int like, String url, User user) {
        return Hospital.builder()
                .seq(seq)
                .name(name)
                .tel(tel)
                .email(email)
                .address(address)
                .open(open)
                .close(close)
                .intro(intro)
                .isApproved(isApproved)
                .like(like)
                .url(url)
                .user(user)
                .build();
    }

}
