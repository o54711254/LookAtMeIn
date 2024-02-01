package com.ssafy.lam.freeboard.domain;

import com.ssafy.lam.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Freeboard {
    @Id
    @GeneratedValue
    @Column(name = "free_board_seq")
    private Long freeboardSeq;

    @Column(name = "free_board_title")
    private String title;

    @Column(name = "free_board_content")
    private String content;

    @Column(name="free_board_cnt")
    private int cnt;

    @Column(name="free_board_regdate")
    private LocalDateTime regdate;;

    @Column(name="free_board_complain")
    private boolean complain;

    @Column(name="free_board_isDeleted")
    private boolean isDeleted;

    @OneToOne
    @JoinColumn(name="user_seq")
    @Column(name="free_board_customer_seq")
    private User user;


    @Builder
    public Freeboard(Long freeboardSeq, String title, String content, int cnt, LocalDateTime regdate, boolean complain, boolean isDeleted, User user) {
        this.freeboardSeq = freeboardSeq;
        this.title = title;
        this.content = content;
        this.cnt = cnt;
        this.regdate = regdate;
        this.complain = complain;
        this.isDeleted = isDeleted;
        this.user = user;
    }
}
