package com.ssafy.lam.requestboard.domain;

import com.ssafy.lam.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Requestboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_board_seq")
    private Long seq;

    @Column(name = "request_board_title")
    private String title;

    @Column(name = "request_board_content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @Column(name = "request_board_requestCnt")
    private int requestCnt;

    @Column(name = "request_board_cnt")
    private int cnt;

    private boolean isDeleted;

    private LocalDate regDate;

    @OneToMany(mappedBy = "requestboard", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Surgery> surgeries;

    @Builder
    public Requestboard(String title, String content, User user, int requestCnt, int cnt, boolean isDeleted, LocalDate regDate) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.requestCnt = requestCnt;
        this.cnt = cnt;
        this.isDeleted = isDeleted;
        this.regDate = regDate;
    }
}
