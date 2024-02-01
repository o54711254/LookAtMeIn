package com.ssafy.lam.reviewBoard.service;

import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import com.ssafy.lam.reviewBoard.dto.ReviewBoardDto;
import com.ssafy.lam.reviewBoard.domain.ReviewBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewBoardServiceImpl implements ReviewBoardService{

    private final ReviewBoardRepository reviewBoardRepository;

    @Override
    public List<ReviewBoard> getAllReviews() {
        return reviewBoardRepository.findAll();
    }

    @Override
    public ReviewBoard getReview(long seq) {
        return reviewBoardRepository.findById(seq).orElse(null);
    }

//    @Override
//    public List<ReviewBoard> searchReview(String type, String keyword) { // Optional?
//        List<ReviewBoard> searchList = null;
//        switch (type) {
//            case "hospital":
//                searchList = getReviewByHospital(keyword);
////                searchList = reviewBoardRepository.findByHospital(keyword);
////                return reviewBoardRepository.findByHospital(keyword);
//            case "doctor":
//                searchList = getReviewByDoctor(keyword);
////                searchList = reviewBoardRepository.findByDoctor(keyword);
////                return reviewBoardRepository.findByDoctor(keyword);
//            case "surgery":
//                searchList = getReviewBySurgery(keyword);
////                searchList = reviewBoardRepository.findBySurgery(keyword);
////                return reviewBoardRepository.findBySurgery(keyword);
//            case "region":
//                searchList = getReviewByRegion(keyword);
////                searchList = reviewBoardRepository.findByRegion(keyword);
////                return reviewBoardRepository.findByRegion(keyword);
//        }
//        return searchList;
//    }

    @Override
    public List<ReviewBoard> getReviewByHospital(String hospital) {
        return reviewBoardRepository.findByHospitalContaining(hospital);
    }

    @Override
    public List<ReviewBoard> getReviewByDoctor(String doctor) {
        return reviewBoardRepository.findByDoctorContaining(doctor);
    }

    @Override
    public List<ReviewBoard> getReviewBySurgery(String surgery) {
        return reviewBoardRepository.findBySurgeryContaining(surgery);
    }

    @Override
    public List<ReviewBoard> getReviewByRegion(String region) {
        return reviewBoardRepository.findByRegionContaining(region);
    }

    @Override
    public  List<ReviewBoard> getReviewByCustomerSeq(long customerSeq) {
        return reviewBoardRepository.findAllByCustomerCustomerSeq(customerSeq);
    }

    @Override
    public ReviewBoard createReview(ReviewBoardDto reviewBoardDto) {
        Customer customer = Customer.builder().customerSeq(reviewBoardDto.getCustomerSeq()).build();
        ReviewBoard reviewBoard = ReviewBoard.builder()
                .seq(reviewBoardDto.getReviewSeq())
                .title(reviewBoardDto.getReviewTitle())
                .content(reviewBoardDto.getReviewContent())
                .hospital(reviewBoardDto.getReviewHospital())
                .doctor(reviewBoardDto.getReviewDoctor())
                .surgery(reviewBoardDto.getReviewSurgery())
                .region(reviewBoardDto.getReviewRegion())
                .score(reviewBoardDto.getReviewScore())
                .price(reviewBoardDto.getReviewPrice())
                .regdate(reviewBoardDto.getReviewRegdate())
                .complain(reviewBoardDto.isReviewComplain())
                .isdeleted(reviewBoardDto.isReviewIsdeleted())
                .customer(customer)
                .build();
        return reviewBoardRepository.save(reviewBoard);
    }

    @Override
    public ReviewBoard updateReview(long seq, ReviewBoardDto reviewBoardDto) {
        Customer customer = Customer.builder().customerSeq(reviewBoardDto.getCustomerSeq()).build();
        ReviewBoard reviewBoard = ReviewBoard.builder()
                .seq(seq)
                .title(reviewBoardDto.getReviewTitle())
                .content(reviewBoardDto.getReviewContent())
                .hospital(reviewBoardDto.getReviewHospital())
                .doctor(reviewBoardDto.getReviewDoctor())
                .surgery(reviewBoardDto.getReviewSurgery())
                .region(reviewBoardDto.getReviewRegion())
                .score(reviewBoardDto.getReviewScore())
                .price(reviewBoardDto.getReviewPrice())
                .regdate(reviewBoardDto.getReviewRegdate())
                .complain(reviewBoardDto.isReviewComplain())
                .isdeleted(reviewBoardDto.isReviewIsdeleted())
                .customer(customer)
                .build();
        return reviewBoardRepository.save(reviewBoard);
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
}
