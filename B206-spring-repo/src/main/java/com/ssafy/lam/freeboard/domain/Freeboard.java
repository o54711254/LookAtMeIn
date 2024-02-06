package com.ssafy.lam.freeboard.domain;

import com.ssafy.lam.file.domain.UploadFile;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "free_board_seq")
    private Long freeboardSeq;

    @Column(name = "free_board_title")
    private String title;

    @Column(name = "free_board_content")
    private String content;

    @Column(name = "free_board_cnt")
    private int cnt;

    @Column(name = "free_board_regdate")
    private LocalDateTime registerDate;
    ;

    @Column(name = "free_board_complain")
    private boolean complain;

    @Column(name = "free_board_isDeleted")
    private boolean isDeleted;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upload_file_seq")
    private UploadFile uploadFile;

    public void setDeleted(boolean deleted) {
        this.isDeleted = deleted;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_board_customer_seq")
    private User user;


//    @OneToMany(mappedBy = "freeboard", fetch = FetchType.LAZY)
//    private List<Comment> comments;


    @Builder
    public Freeboard(Long freeboardSeq, String title, String content, int cnt, LocalDateTime registerDate, boolean complain, boolean isDeleted, UploadFile uploadFile, User user) {
        this.freeboardSeq = freeboardSeq;
        this.title = title;
        this.content = content;
        this.cnt = cnt;
        this.registerDate = registerDate;
        this.complain = complain;
        this.isDeleted = isDeleted;
        this.uploadFile = uploadFile;
        this.user = user;
    }
}
