package com.ssafy.lam.tag.service;

import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import com.ssafy.lam.tag.domain.Hashtag;
import com.ssafy.lam.tag.domain.ReviewHashtag;
import com.ssafy.lam.tag.domain.ReviewHashtagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ReviewHashtagService {
    private final HashtagService hashtagService;
    private final ReviewHashtagRepository reviewHashtagRepository;

    public void saveHashtag(ReviewBoard reviewBoard, List<String> tagNames) {
        tagNames.forEach(tagName -> {
            Hashtag hashtag = hashtagService.findByTagName(tagName)
                    .orElseGet(() -> hashtagService.save(tagName));

            ReviewHashtag reviewHashtag = new ReviewHashtag(reviewBoard, hashtag);
            reviewHashtagRepository.save(reviewHashtag);
        });
    }

//    private Long mapHashtagTOReview(Long reviewSeq, ReviewBoard reviewBoard, Hashtag hashtag) {
//        return reviewHashtagRepository.save(new ReviewHashtag(reviewSeq, reviewBoard, hashtag)).getReviewSeq();
//    }
//
//    public List<ReviewHashtag> findHashtagListByReviewBoard(ReviewBoard reviewBoard) {
//        return reviewHashtagRepository.findByReviewBoard(reviewBoard);
//    }
}