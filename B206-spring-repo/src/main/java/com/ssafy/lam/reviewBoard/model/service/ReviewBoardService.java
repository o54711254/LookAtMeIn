package com.ssafy.lam.reviewBoard.model.service;

import com.ssafy.lam.entity.ReviewBoard;

import java.util.List;

public interface ReviewBoardService {

    // 게시판 전체조회
    List<ReviewBoard> getAllReviews();

    // 게시판 상세조회
    ReviewBoard getReview(long seq);

    // 게시판 조건조회 - 조건 별 검색
//    List<ReviewBoard> searchReview(String type, String keyword);
    // 게시판 수술 부위로 조회
    List<ReviewBoard> getReviewBySurgery(String surgery);
    // 게시판 지역으로 조회
    List<ReviewBoard> getReviewByRegion(String region);
    // 게시판 병원으로 조회
    List<ReviewBoard> getReviewByHospital(String hospital);
    // 게시판 의사로 조회
    List<ReviewBoard> getReviewByDoctor(String doctor);


    // 게시판 작성
    ReviewBoard createReview(ReviewBoard reviewBoard);

    // 게시판 수정
    ReviewBoard updateReview(long seq, ReviewBoard reviewBoard);

    // 게시판 삭제(해당 글 비활성화)
    void deactivateReview(long seq);

}
