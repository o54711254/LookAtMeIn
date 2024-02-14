package com.ssafy.lam.hospital.service;

import com.ssafy.lam.config.MultipartConfig;
import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.file.service.UploadFileService;
import com.ssafy.lam.hospital.domain.*;
import com.ssafy.lam.hospital.dto.DoctorDto;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService{

    private final DoctorRepository doctorRepository;
    private final CareerRepository careerRepository;
    private final HospitalRepository hospitalRepository;
    private final HospitalCategoryRepository hospitalCategoryRepository;

    private final DoctorCategoryRepository doctorCategoryRepository;
    private final UploadFileService uploadFileService;

    MultipartConfig multipartConfig = new MultipartConfig();
    private String uploadPath = multipartConfig.multipartConfigElement().getLocation();



    @Override
    public Doctor getDoctor(Long doctorSeq) {
        return doctorRepository.findById(doctorSeq).orElse(null);
    }

    @Override
    public Doctor updateDoctor(Long doctorSeq, DoctorDto doctorDto, MultipartFile doctorProfile) {
//        Doctor doctor = doctorRepository.findById(doctorSeq).orElse(null);
        Doctor currentDoctor = doctorRepository.findById(doctorSeq).orElse(null);
        Hospital hospital = hospitalRepository.findById(currentDoctor.getHospital().getHospitalSeq()).orElse(null);
        Doctor doctor = Doctor.builder()
                .docInfoSeq(doctorSeq)
                .docInfoName(doctorDto.getDoc_info_name())
                .docInfoCategory(doctorDto.getDoc_info_category())
                .hospital(hospital)
                .build();
        if(doctorProfile != null){
            UploadFile uploadFile = uploadFileService.store(doctorProfile);
            doctor.setProfile(uploadFile);
        }
        return doctorRepository.save(doctor);
    }

//    @Override
//    public Doctor deleteDoctor(Long doctorSeq) {
//        Optional<Doctor> doctor = doctorRepository.findById(doctorSeq);
//        if(doctor.isPresent()) {
//
//        }
//
//    }

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