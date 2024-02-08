package com.ssafy.lam.hospital.service;

import com.ssafy.lam.hospital.domain.*;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService{

    private final DoctorRepository doctorRepository;
    private final CareerRepository careerRepository;
    private final CategoryRepository categoryRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository, CareerRepository careerRepository,
                             CategoryRepository categoryRepository) {
        this.doctorRepository = doctorRepository;
        this.careerRepository = careerRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Doctor getDoctor(Long doctorSeq) {
        return doctorRepository.findById(doctorSeq).orElse(null);
    }

    @Override
    public List<Category> getCategory(Long doctorSeq) {
        return categoryRepository.findAllByDoctorDocInfoSeq(doctorSeq);
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
