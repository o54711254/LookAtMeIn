package com.ssafy.lam.image.domain;

import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.freeboard.domain.Freeboard;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_seq")
    private long imageSeq; // 이미지 번호

    @Column(name = "image_category")
    private String category; // 이미지 카테고리

    @Column(name = "image_realName")
    private String realName; // 사진원본

    @Column(name = "image_sysName")
    private String sysName; // 사진복사본

    @Builder
    public Image(long imageSeq, String category, String realName, String sysName) {
        this.imageSeq = imageSeq;
        this.category = category;
        this.realName = realName;
        this.sysName = sysName;
    }
}
