package com.ssafy.lam.questionnaire.domain;

import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Questionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    private String blood;
    private String remark;
    private String title;
    private String content;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upload_file_seq")
    private UploadFile uploadFile;

    @Builder
    public Questionnaire(Long seq, String blood, String remark, String title, String content, UploadFile uploadFile) {
        this.seq = seq;
        this.blood = blood;
        this.remark = remark;
        this.title = title;
        this.content = content;
        this.uploadFile = uploadFile;
    }
}
