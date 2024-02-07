package com.ssafy.lam.reviewBoard.service;

import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import com.ssafy.lam.reviewBoard.domain.ReviewBoardRepository;
import com.ssafy.lam.reviewBoard.dto.ReviewBoardRegister;
import com.ssafy.lam.reviewBoard.dto.ReviewBoardUpdate;
import com.ssafy.lam.reviewBoard.dto.ReviewListDisplay;
import com.ssafy.lam.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewBoardServiceImpl implements ReviewBoardService{

    private final ReviewBoardRepository reviewBoardRepository;

    @Override
    public List<ReviewBoard> getAllReviews() {
        return reviewBoardRepository.findByIsdeletedFalse();
    }

    @Override
    public ReviewBoard getReview(long seq) {
        int addview = 1;
        ReviewBoard review = reviewBoardRepository.findById(seq).orElse(null);
        if(review==null || review.isIsdeleted())
            return null;
        addview += review.getCnt();
        review.setCnt(addview);
        reviewBoardRepository.save(review);
        return reviewBoardRepository.findById(seq).orElse(null);
    }

    @Override
    public ReviewBoard createReview(ReviewBoardRegister reviewBoardRegister) {
        User user = User.builder().name(reviewBoardRegister.getUsername()).userSeq(reviewBoardRegister.getUser_seq()).build();
        LocalDate now = LocalDate.now();
        long date = now.getYear() * 10000L + now.getMonthValue() * 100 + now.getDayOfMonth();
        ReviewBoard reviewBoard = ReviewBoard.builder()
                .title(reviewBoardRegister.getReviewBoard_title())
                .content(reviewBoardRegister.getReviewBoard_content())
                .hospital(reviewBoardRegister.getReviewBoard_hospital())
                .doctor(reviewBoardRegister.getReviewBoard_doctor())
                .surgery(reviewBoardRegister.getReviewBoard_surgery())
                .region(reviewBoardRegister.getReviewBoard_region())
                .score(reviewBoardRegister.getReviewBoard_score())
                .user(user)
                .price(reviewBoardRegister.getReviewBoard_price())
                .regdate(date)
                .build();
        return reviewBoardRepository.save(reviewBoard);
    }

    @Override
    public void updateReview(ReviewBoardUpdate reviewBoardUpdate) {
        ReviewBoard reviewBoard = reviewBoardRepository.findById(reviewBoardUpdate.getReviewBoard_seq()).orElse(null);
        if(reviewBoard!=null) {
            reviewBoard.setTitle(reviewBoardUpdate.getReviewBoard_title());
            reviewBoard.setContent(reviewBoardUpdate.getReviewBoard_content());
            reviewBoard.setHospital(reviewBoardUpdate.getReviewBoard_hospital());
            reviewBoard.setDoctor(reviewBoardUpdate.getReviewBoard_doctor());
            reviewBoard.setSurgery(reviewBoardUpdate.getReviewBoard_surgery());
            reviewBoard.setRegion(reviewBoardUpdate.getReviewBoard_region());
            reviewBoard.setScore(reviewBoardUpdate.getReviewBoard_score());
            reviewBoard.setPrice(reviewBoardUpdate.getReviewBoard_price());
            reviewBoardRepository.save(reviewBoard);
        }
    }

    @Override
    public void deactivateReview(long seq) {
        Optional<ReviewBoard> removeReview = reviewBoardRepository.findById(seq);
        if(removeReview.isPresent()) {
            ReviewBoard selectedReview = removeReview.get();
            selectedReview.setIsdeleted(true);
            reviewBoardRepository.save(selectedReview);
        }
    }

    @Override
    public List<ReviewListDisplay> getReviewByUserSeq(Long userSeq) {
       return reviewBoardRepository.findAllByUserUserSeqAndIsdeletedFalse(userSeq).stream()
               .map(reviewBoard -> new ReviewListDisplay(
                       reviewBoard.getReviewBoard_seq(),
                       reviewBoard.getCustomer_name(),
                       reviewBoard.getReviewBoard_title(),
                       reviewBoard.getReviewBoard_cnt(),
                       reviewBoard.getReviewBoard_regDate(),
                       reviewBoard.getReviewBoard_score(),
                       reviewBoard.getReviewBoard_doctor(),
                       reviewBoard.getReviewBoard_region(),
                       reviewBoard.getReviewBoard_surgery(),
                       reviewBoard.getReviewBoard_hospital(),
                       reviewBoard.getReviewBoard_price()
               )).collect(Collectors.toList());
    }

    @Override
    public void reportReview(Long seq) {
        Optional<ReviewBoard> reportedReview = reviewBoardRepository.findById(seq);
        if(reportedReview.isPresent()) {
            ReviewBoard selectedReview = reportedReview.get();
            selectedReview.setComplain(true);
            reviewBoardRepository.save(selectedReview);
        }
    }

}
