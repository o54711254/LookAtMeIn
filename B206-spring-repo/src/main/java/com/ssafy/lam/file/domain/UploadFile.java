package com.ssafy.lam.file.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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
    public UploadFile(String category, String name) {
        this.category = category;
        this.name = name;
    }
}
