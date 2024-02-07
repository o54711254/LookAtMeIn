package com.ssafy.lam.hospital.controller;

import com.ssafy.lam.hospital.domain.Career;
import com.ssafy.lam.hospital.domain.Category;
import com.ssafy.lam.hospital.domain.Doctor;
import com.ssafy.lam.hospital.domain.DoctorRepository;
import com.ssafy.lam.hospital.dto.CareerDto;
import com.ssafy.lam.hospital.dto.CategoryDto;
import com.ssafy.lam.hospital.dto.DoctorDto;
import com.ssafy.lam.hospital.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/{doctor_seq}")
    @Operation(summary = "의사 정보를 조회한다.")
    public ResponseEntity<DoctorDto> getDoctor(@PathVariable Long doctor_seq) {
        List<Category> category = doctorService.getCategory(doctor_seq);
        List<CategoryDto> categoryDto = new ArrayList<>();
        for(Category c : category) {
            categoryDto.add(new CategoryDto(c.getPart()));
        }

        List<Career> career = doctorService.getCareer(doctor_seq);
        List<CareerDto> careerDto = new ArrayList<>();
        for(Career c : career) {
            careerDto.add(new CareerDto(c.getCareerStart(), c.getCareerEnd(), c.getCareerContent()));
        }

        Doctor doctor = doctorService.getDoctor(doctor_seq);
        DoctorDto doctorDto = DoctorDto.builder()
                .doc_info_seq(doctor_seq)
                .doc_info_name(doctor.getDocInfoName())
                .doc_info_category(categoryDto)
                .doc_info_career(careerDto)
                .build();
        return new ResponseEntity<>(doctorDto, HttpStatus.OK);
    }

}
