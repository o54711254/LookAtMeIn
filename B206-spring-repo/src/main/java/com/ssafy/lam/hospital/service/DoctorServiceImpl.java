package com.ssafy.lam.hospital.service;

import com.ssafy.lam.hospital.domain.*;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService{

    private final DoctorRepository doctorRepository;
    private final CareerRepository careerRepository;
    private final HospitalCategoryRepository hospitalCategoryRepository;

    private final DoctorCategoryRepository doctorCategoryRepository;


    @Override
    public Doctor getDoctor(Long doctorSeq) {
        return doctorRepository.findById(doctorSeq).orElse(null);
    }

    @Override
    public List<DoctorCategory> getCategory(Long doctorSeq) {
        return doctorCategoryRepository.findAllByDoctorDocInfoSeq(doctorSeq);
    }

    @Override
    public List<Career> getCareer(Long doctorSeq) {
        return careerRepository.findAllByDoctorDocInfoSeq(doctorSeq);
    }

    @Override
    public List<ReviewBoard> getReviewsByDoctor(Long doctorSeq) {
        return doctorRepository.findReviewsByDoctorSeq(doctorSeq);
    }

    @Override
    public double getAvgScore(Long doctorSeq) {
        return doctorRepository.findAvgByDoctorSeq(doctorSeq).orElse(0.0);
    }

    @Override
    public int getCntReviews(Long doctorSeq) {
        return doctorRepository.countByDoctorSeq(doctorSeq);
    }

}
