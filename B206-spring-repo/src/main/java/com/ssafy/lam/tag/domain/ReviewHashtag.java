package com.ssafy.lam.tag.domain;

import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class ReviewHashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewSeq;

    @ManyToOne
    @JoinColumn(name = "review_board_seq")
    private ReviewBoard reviewBoard;

    @ManyToOne
    @JoinColumn(name = "hashtag_Seq")
    private Hashtag hashtag;

    @Builder
    public ReviewHashtag(ReviewBoard reviewBoard, Hashtag hashtag) {
        this.reviewBoard = reviewBoard;
        this.hashtag = hashtag;
    }
}