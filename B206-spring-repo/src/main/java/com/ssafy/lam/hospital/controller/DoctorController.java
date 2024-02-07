package com.ssafy.lam.hospital.controller;

import com.ssafy.lam.hospital.domain.Doctor;
import com.ssafy.lam.hospital.domain.DoctorRepository;
import com.ssafy.lam.hospital.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

//    @GetMapping("/{doctor_seq}")
//    @Operation(summary = "의사 정보를 조회한다.")
//    public ResponseEntity<Doctor> getDoctor(@PathVariable Long doctor_seq) {
//        Doctor doctor = doctorService.getDoctor(doctor_seq);
//        return new ResponseEntity<>(doctor, HttpStatus.OK);
//    }

}
