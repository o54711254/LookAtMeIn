package com.ssafy.lam.comment.domain;

import com.ssafy.lam.freeboard.domain.Freeboard;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "comment_seq")
    private Long seq;

    @Column(name = "comment_content")
    private String content;

    @Column(name="comment_userid")
    private String userId;

    @Column(name="comment_regdate")
    private LocalDateTime regdate;

    @Column(name="comment_complain")
    private boolean complain;

    @Column(name="comment_isdeleted")
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_board_seq")
    private Freeboard freeboard;

    @Builder

    public Comment(Long seq, String content, String userId, LocalDateTime regdate, boolean complain, boolean isDeleted, Freeboard freeboard) {
        this.seq = seq;
        this.content = content;
        this.userId = userId;
        this.regdate = regdate;
        this.complain = complain;
        this.isDeleted = isDeleted;
        this.freeboard = freeboard;
    }
}
