package com.ssafy.lam.reviewBoard.service;

import com.ssafy.lam.config.MultipartConfig;
import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.file.service.UploadFileService;
import com.ssafy.lam.hospital.domain.Doctor;
import com.ssafy.lam.hospital.domain.DoctorRepository;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.domain.HospitalRepository;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import com.ssafy.lam.reviewBoard.domain.ReviewBoardRepository;
import com.ssafy.lam.reviewBoard.dto.ReviewBoardRegister;
import com.ssafy.lam.reviewBoard.dto.ReviewBoardUpdate;
import com.ssafy.lam.reviewBoard.dto.ReviewListDisplay;
import com.ssafy.lam.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewBoardServiceImpl implements ReviewBoardService {

    private final ReviewBoardRepository reviewBoardRepository;
    MultipartConfig multipartConfig = new MultipartConfig();

    // 파일이 업로드될 디렉토리 경로
    private String uploadPath = multipartConfig.multipartConfigElement().getLocation();
    private final UploadFileService uploadFileService;
    private final HospitalRepository hospitalRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public List<ReviewBoard> getAllReviews() {
        return reviewBoardRepository.findByIsdeletedFalse();
    }

    @Override
    public ReviewBoard getReview(long seq) {
        int addview = 1;
        ReviewBoard review = reviewBoardRepository.findById(seq).orElse(null);
        if (review == null || review.isIsdeleted())
            return null;
        addview += review.getCnt();
        review.setCnt(addview);
        return reviewBoardRepository.save(review);
//        return reviewBoardRepository.findById(seq).orElse(null);
    }

    @Override
    public ReviewBoard createReview(ReviewBoardRegister reviewBoardRegister, MultipartFile file) {
        User user = User.builder()
                .name(reviewBoardRegister.getUsername())
                .userSeq(reviewBoardRegister.getUser_seq())
                .build();
        Hospital hospital = hospitalRepository.findById(reviewBoardRegister.getHospital_seq()).orElse(null);
        Doctor doctor = doctorRepository.findById(reviewBoardRegister.getDoctor_seq()).orElse(null);
        LocalDate now = LocalDate.now();
        long date = now.getYear() * 10000L + now.getMonthValue() * 100 + now.getDayOfMonth();


        UploadFile uploadFile = uploadFileService.store(file);

        ReviewBoard reviewBoard = ReviewBoard.builder()
                .title(reviewBoardRegister.getReviewBoard_title())
                .content(reviewBoardRegister.getReviewBoard_content())
//                .hospital(reviewBoardRepository.findHospitalNameByHospitalSeq(reviewBoardRegister.getHospital_seq()))
//                .hospital_seq(reviewBoardRegister.getHospital_seq())
//                .doctor(reviewBoardRepository.findDoctorNameByDoctorSeq(reviewBoardRegister.getDoctor_seq()))
//                .doctor_seq(reviewBoardRegister.getDoctor_seq())
                .surgery(reviewBoardRegister.getReviewBoard_surgery())
                .region(reviewBoardRegister.getReviewBoard_region())
                .score(reviewBoardRegister.getReviewBoard_score())
                .user(user)
                .expectedPrice(reviewBoardRegister.getReviewBoard_expected_price())
                .surgeryPrice(reviewBoardRegister.getReviewBoard_surgery_price())
                .regdate(date)
                // .uploadFile(uploadFile)
                .hospital(hospital)
                .doctor(doctor)
                .build();
        return reviewBoardRepository.save(reviewBoard);
    }


    @Override
    public void updateReview(ReviewBoardUpdate reviewBoardUpdate) {
        ReviewBoard reviewBoard = reviewBoardRepository.findById(reviewBoardUpdate.getReviewBoard_seq()).orElse(null);
        Hospital hospital = hospitalRepository.findById(reviewBoardUpdate.getHospital_seq()).orElse(null);
        Doctor doctor = doctorRepository.findById(reviewBoardUpdate.getDoctor_seq()).orElse(null);
        if(reviewBoard!=null) {
            reviewBoard.setTitle(reviewBoardUpdate.getReviewBoard_title());
            reviewBoard.setContent(reviewBoardUpdate.getReviewBoard_content());
//            reviewBoard.setHospital(reviewBoardRepository.findHospitalNameByHospitalSeq(reviewBoardUpdate.getHospital_seq()));
//            reviewBoard.setDoctor(reviewBoardRepository.findDoctorNameByDoctorSeq(reviewBoardUpdate.getDoctor_seq()));
            reviewBoard.setRegion(reviewBoardUpdate.getReviewBoard_region());
            reviewBoard.setScore(reviewBoardUpdate.getReviewBoard_score());
            reviewBoard.setExpectedPrice(reviewBoardUpdate.getReviewBoard_expected_price());
            reviewBoard.setSurgeryPrice(reviewBoardUpdate.getReviewBoard_surgery_price());
            reviewBoard.setHospital(hospital);
            reviewBoard.setDoctor(doctor);
//            reviewBoard.setHospital_seq(reviewBoardUpdate.getHospital_seq());
//            reviewBoard.setDoctor_seq(reviewBoardUpdate.getDoctor_seq());
            reviewBoardRepository.save(reviewBoard);
        }
    }

    @Override
    public void deactivateReview(long seq) {
        Optional<ReviewBoard> removeReview = reviewBoardRepository.findById(seq);
        if (removeReview.isPresent()) {
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
                        reviewBoard.getReviewBoard_expected_price(),
                        reviewBoard.getReviewBoard_surgery_price()
                )).collect(Collectors.toList());
    }

    @Override
    public void reportReview(Long seq) {
        Optional<ReviewBoard> reportedReview = reviewBoardRepository.findById(seq);
        if (reportedReview.isPresent()) {
            ReviewBoard selectedReview = reportedReview.get();
            selectedReview.setComplain(true);
            reviewBoardRepository.save(selectedReview);
        }
    }

}
