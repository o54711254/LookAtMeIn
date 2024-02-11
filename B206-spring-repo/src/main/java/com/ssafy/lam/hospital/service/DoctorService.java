package com.ssafy.lam.hospital.service;

import com.ssafy.lam.hospital.domain.Career;
import com.ssafy.lam.hospital.domain.DoctorCategory;
import com.ssafy.lam.hospital.domain.HospitalCategory;
import com.ssafy.lam.hospital.domain.Doctor;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;

import java.util.List;

public interface DoctorService {

    Doctor getDoctor(Long doctorSeq);
    List<DoctorCategory> getCategory(Long categorySeq);
    List<Career> getCareer(Long careerSeq);

    List<ReviewBoard> getReviewsByDoctor(Long doctorSeq);
    double getAvgScore(Long doctorSeq);
    int getCntReviews(Long doctorSeq);

}
