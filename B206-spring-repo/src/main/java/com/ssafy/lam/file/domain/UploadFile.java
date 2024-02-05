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

    @Column(name = "original_path")
    private String originalPath; // 파일 경로, 성헝 전 사진 경로

    @Column(name = "after_path")
    private String afterPath; // 성형 후 사진 경로

    @Builder
    public UploadFile(long seq, String category, String originalPath, String afterPath) {
        this.seq = seq;
        this.category = category;
        this.originalPath = originalPath;
        this.afterPath = afterPath;
    }
}
