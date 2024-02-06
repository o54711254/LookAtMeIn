package com.ssafy.lam.hospital.service;

import com.ssafy.lam.hospital.domain.Doctor;
import com.ssafy.lam.hospital.domain.DoctorRepository;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService{

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor getDoctor(Long doctorSeq) {
        return doctorRepository.findById(doctorSeq).orElse(null);
    }

}
