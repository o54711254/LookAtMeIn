package com.ssafy.lam.coordinator.dto;

import com.ssafy.lam.hospital.dto.Hospital;
import com.ssafy.lam.user.dto.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("3")
public class Coordinator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coordinator_seq")
    private Long seq;
    @Column(name = "coordinator_name")
    private String name;
    @Column(name = "coordinator_isApproved")
    private boolean isApproved;

    @OneToOne
    @JoinColumn(name = "member_seq")
    private User user;
    @ManyToOne
    @JoinColumn(name = "hos_info_seq")
    private Hospital hospital;

    @Builder
    public Coordinator(Long seq, String name, boolean isApproved, User user, Hospital hospital) {
        this.seq = seq;
        this.name = name;
        this.isApproved = isApproved;
        this.user = user;
        this.hospital = hospital;
    }

    public Coordinator toEntity(Long seq, String name, boolean isApproved, User user, Hospital hospital) {
        return Coordinator.builder()
                .seq(seq)
                .name(name)
                .isApproved(isApproved)
                .user(user)
                .hospital(hospital)
                .build();
    }

}
