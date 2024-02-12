package com.ssafy.lam.hashtag.domain;

import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Entity
@Setter
public class ReviewHashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne
    private ReviewBoard reviewBoard;

    @ManyToOne
    private Hashtag hashtag;

    private LocalDate regDate;

    @Builder
    public ReviewHashtag(Long seq, ReviewBoard reviewBoard, Hashtag hashtag, LocalDate regDate) {
        this.seq = seq;
        this.reviewBoard = reviewBoard;
        this.hashtag = hashtag;
        this.regDate = regDate;
    }
}