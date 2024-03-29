package com.ssafy.lam.hospital.domain;
import com.ssafy.lam.coordinator.domain.Coordinator;
import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "hos_info")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hos_info_seq")
    private Long hospitalSeq;

    //    @ManyToOne
    @OneToOne
    @JoinColumn(name = "user_seq")
    private User user;

    @OneToMany(mappedBy = "hospital")
    private List<Coordinator> coordinators = new ArrayList<>();

    @Column(name="hos_info_tel")
    private String tel;

    @Column(name = "hos_info_email")
    private String email;

    @Column(name = "hos_info_address")
    private String address;

    @Column(name = "hos_info_open")
    private String openTime;

    @Column(name = "hos_info_close")
    private String closeTime;

    @Column(name = "hos_info_intro")
    private String intro;

    @Column(name = "hos_info_isapproved")
    private boolean isApproved;

    @Column(name = "hos_info_bookmark")
    private int bookmark;

    @Column(name = "hos_info_url")
    private String url;

    @Column(name = "hos_info_rejected")
    private boolean isRejected;

    @OneToOne
    @JoinColumn(name = "registration_file_seq")
    private UploadFile registrationFile;

    @OneToOne
    @JoinColumn(name = "profile_file_seq")
    private UploadFile profileFile;
    public void approve() {
        this.isApproved = true;
    }
    public void reject() { this.isRejected = true; }

    @Builder
    public Hospital(Long hospitalSeq, User user, List<Coordinator> coordinators, String tel, String email, String address, String openTime, String closeTime, String intro, boolean isApproved, int bookmark, String url, boolean isRejected, UploadFile registrationFile, UploadFile profileFile) {
        this.hospitalSeq = hospitalSeq;
        this.user = user;
        this.coordinators = coordinators;
        this.tel = tel;
        this.email = email;
        this.address = address;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.intro = intro;
        this.isApproved = isApproved;
        this.bookmark = bookmark;
        this.url = url;
        this.isRejected = isRejected;
        this.registrationFile = registrationFile;
        this.profileFile = profileFile;
    }
}