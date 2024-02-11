package com.ssafy.lam.requestboard.domain;

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
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_board_seq")
    private Requestboard requestboard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @Column(length = 1000)
    private String message;

    @Builder
    public Response(Requestboard requestboard, User user, String message) {
        this.requestboard = requestboard;
        this.user = user;
        this.message = message;
    }
}
