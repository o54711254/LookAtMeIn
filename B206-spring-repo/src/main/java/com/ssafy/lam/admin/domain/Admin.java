package com.ssafy.lam.admin.domain;

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
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_seq")
    private Long adminSeq;

    @OneToOne
    @JoinColumn(name = "user_seq")
    private User user;

    @Builder

    public Admin(Long adminSeq, User user) {
        this.adminSeq = adminSeq;
        this.user = user;
    }
}
