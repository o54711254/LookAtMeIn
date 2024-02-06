package com.ssafy.lam.file.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_seq")
    private long seq; // 이미지 번호

    @Column(name = "category")
    private String category; // 이미지 카테고리

    @Column(name="filename")
    private String name;


    @Builder

    public UploadFile(long seq, String category, String name) {
        this.seq = seq;
        this.category = category;
        this.name = name;
    }
}
