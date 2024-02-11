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
    private Long seq; // 이미지 번호

    @Column(name = "type")
    private String type; // 이미지 카테고리

    @Column(name="filename")
    private String name;


    @Builder

    public UploadFile(Long seq, String type, String name) {
        this.seq = seq;
        this.type = type;
        this.name = name;
    }
}
