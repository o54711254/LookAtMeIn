package com.ssafy.lam.tag.domain;

import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewHashtagRepository extends JpaRepository<ReviewHashtag, Long> {
    List<ReviewHashtag> findByReviewBoard(ReviewBoard reviewBoard);
    List<ReviewHashtag> findByHashtag(Hashtag hashtag);
}
