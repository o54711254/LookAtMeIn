package com.ssafy.lam.hospital.controller;


import com.ssafy.lam.hospital.dto.HospitalDetailDto;
import com.ssafy.lam.hospital.dto.HospitalDto;
import com.ssafy.lam.hospital.dto.HospitalRequestDto;
import com.ssafy.lam.hospital.service.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hospital")
public class HosptialController {
    private final HospitalService hospitalService;
    private Logger log = LoggerFactory.getLogger(HosptialController.class);

    @Autowired
    public HosptialController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @PostMapping("/regist")
    @Operation(summary = "병원 정보를 등록한다.")
    public ResponseEntity<Void> regist(@RequestBody HospitalRequestDto hospitalRequestDto) {
        log.info("regist hospital : {}, Category {}", hospitalRequestDto.getHospital(), hospitalRequestDto.getCategoryList());

        hospitalService.createHospital(hospitalRequestDto.getHospital(), hospitalRequestDto.getCategoryList());
        return ResponseEntity.ok().build();
    }


}
