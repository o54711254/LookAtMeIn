package com.ssafy.lam.hospital.domain;

import com.ssafy.lam.file.domain.UploadFile;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "doc_info")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long docInfoSeq;

    private String docInfoName;

    @ManyToOne
    @JoinColumn(name = "hos_info_seq")
    private Hospital hospital;

    @OneToOne
    private UploadFile profile;


    public void setProfile(UploadFile profile) {
        this.profile = profile;
    }

    @Builder
    public Doctor(Long docInfoSeq, String docInfoName, Hospital hospital) {
        this.docInfoSeq = docInfoSeq;
        this.docInfoName = docInfoName;
        this.hospital = hospital;
    }
}