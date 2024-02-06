package com.ssafy.lam.reviewBoard.service;

import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import com.ssafy.lam.reviewBoard.dto.ReviewBoardRegister;
import com.ssafy.lam.reviewBoard.dto.ReviewBoardUpdate;
import com.ssafy.lam.reviewBoard.dto.ReviewListDisplay;

import java.util.List;

public interface ReviewBoardService {

    // 게시판 전체조회
    List<ReviewBoard> getAllReviews();

    // 게시판 상세조회
    ReviewBoard getReview(long seq);

    // 게시판 작성
    ReviewBoard createReview(ReviewBoardRegister reviewBoardRegister);

    // 게시판 수정
    void updateReview(ReviewBoardUpdate reviewBoardUpdate);

    // 게시판 삭제(해당 글 비활성화)
    void deactivateReview(long seq);
    
    // 게시글 신고
    void reportReview(Long seq);


    List<ReviewListDisplay> getReviewByUserSeq(Long userSeq);
}
