package com.ssafy.lam.requestboard.domain;

import com.ssafy.lam.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User recipient; // 알림을 받는 사용자

    private String message;
    private boolean isRead = false;

    @Builder
    public Notification(User recipient, String message, boolean isRead) {
        this.recipient = recipient;
        this.message = message;
        this.isRead = isRead;
    }
}