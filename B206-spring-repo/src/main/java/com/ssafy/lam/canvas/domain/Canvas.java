package com.ssafy.lam.canvas.domain;

import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Canvas {
    @Id
    @GeneratedValue
    private Long seq;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_user_seq")
    private User customer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "before_seq")
    private UploadFile before;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "after_seq")
    private UploadFile after;

    @Builder
    public Canvas(Long seq, User customer, UploadFile before, UploadFile after) {
        this.seq = seq;
        this.customer = customer;
        this.before = before;
        this.after = after;
    }
}
