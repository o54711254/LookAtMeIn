package com.ssafy.lam.reviewBoard.model.service;

import com.ssafy.lam.entity.ReviewBoard;
import com.ssafy.lam.reviewBoard.model.repository.ReviewBoardRepository;
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
    public ReviewBoard createReview(ReviewBoard reviewBoard) {
        return reviewBoardRepository.save(reviewBoard);
    }

    @Override
    public ReviewBoard updateReview(long seq, ReviewBoard newReview) {
        ReviewBoard selectedReview = newReview.toEntity(seq, newReview.getTitle(), newReview.getContent(),
                newReview.getHospital(), newReview.getDoctor(), newReview.getSurgery(), newReview.getRegion(),
                newReview.getScore(), newReview.getPrice(), newReview.getRegdate(), newReview.isComplain(),
                newReview.isIsdeleted());
        return reviewBoardRepository.save(selectedReview);
    }

    @Override
    public void deactivateReview(long seq) {
        Optional<ReviewBoard> removeReview = reviewBoardRepository.findById(seq);
        if(removeReview.isPresent()) {
            ReviewBoard selectedReview = removeReview.get();
            selectedReview.setIsdeleted(true);
        }
    }
}
