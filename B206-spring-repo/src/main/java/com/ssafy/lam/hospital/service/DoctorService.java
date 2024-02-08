package com.ssafy.lam.hospital.service;

import com.ssafy.lam.hospital.domain.Career;
import com.ssafy.lam.hospital.domain.Category;
import com.ssafy.lam.hospital.domain.Doctor;
import com.ssafy.lam.hospital.dto.CareerDto;
import com.ssafy.lam.hospital.dto.CategoryDto;
import com.ssafy.lam.hospital.dto.DoctorDto;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;

import java.util.List;

public interface DoctorService {

    Doctor getDoctor(Long doctorSeq);
    List<Category> getCategory(Long categorySeq);
    List<Career> getCareer(Long careerSeq);

    List<ReviewBoard> getReviewsByDoctor(Long doctorSeq);
    double getAvgScore(Long doctorSeq);
    int getCntReviews(Long doctorSeq);

}
