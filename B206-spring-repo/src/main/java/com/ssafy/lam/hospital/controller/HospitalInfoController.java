package com.ssafy.lam.hospital.controller;

import com.ssafy.lam.common.EncodeFile;
import com.ssafy.lam.config.MultipartConfig;
import com.ssafy.lam.hospital.domain.DoctorCategory;
import com.ssafy.lam.hospital.domain.HospitalCategory;
import com.ssafy.lam.hospital.domain.Doctor;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.dto.CategoryDto;
import com.ssafy.lam.hospital.dto.DoctorListDto;
import com.ssafy.lam.hospital.dto.HospitalDetailDto;
import com.ssafy.lam.hospital.service.DoctorService;
import com.ssafy.lam.hospital.service.HospitalService;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import com.ssafy.lam.reviewBoard.dto.ReviewListDisplay;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/hospital-info")
public class HospitalInfoController {

    private final HospitalService hospitalService;
    private final DoctorService doctorService;
    private Logger log = LoggerFactory.getLogger(HosptialController.class);

    MultipartConfig multipartConfig = new MultipartConfig();
    private String uploadPath = multipartConfig.multipartConfigElement().getLocation();

    @Autowired
    public HospitalInfoController(HospitalService hospitalService, DoctorService doctorService) {
        this.hospitalService = hospitalService;
        this.doctorService = doctorService;
    }


    @GetMapping("/list")
    @Operation(summary = "고객이 병원 전체 목록을 조회한다.")
    public ResponseEntity<List<HospitalDetailDto>> getAllHospital() {
        List<Hospital> hospitalList = hospitalService.getAllHospitalInfo();
        List<HospitalDetailDto> hospitalDetailDtoList = new ArrayList<>();
        log.info("병원 목록: " + hospitalList);
        try{
            for(Hospital h : hospitalList) {
                log.info("병원 정보: " + h);
                HospitalDetailDto hospitalDetailDto = HospitalDetailDto.builder()
                        .hospitalInfo_seq(h.getHospitalSeq())
                        .hospitalInfo_name(h.getUser().getName())
                        .hospitalInfo_phoneNumber(h.getTel())
                        .hospitalInfo_introduce(h.getIntro())
                        .hospitalInfo_address(h.getAddress())
                        .hospitalInfo_open(h.getOpenTime())
                        .hospitalInfo_close(h.getCloseTime())
                        .hospitalInfo_url(h.getUrl())
                        .userSeq(h.getUser().getUserSeq())
                        .build();
                log.info("병원 정보: " + hospitalDetailDto);
//                if(h.getRegistrationFile() != null) { // 병원 목록 조회 시 오류 발생
//                    // 병원 등록증 base64로 인코딩해서 보내줘야함
//                    Path path = Paths.get(uploadPath + "/" + h.getRegistrationFile().getName());
//                    String profileBase64 = EncodeFile.encodeFileToBase64(path);
//                    hospitalDetailDto.setProfileBase64(profileBase64);
//                }
                hospitalDetailDtoList.add(hospitalDetailDto);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(hospitalDetailDtoList, HttpStatus.OK);
    }

//    @PostMapping("/detail/{hospital_seq}")
//    @Operation(summary = "고객이 병원 상세 페이지를 조회한다. - 병원 상세 정보")
//    public ResponseEntity<HospitalDetailDto> getHospitalBySeq(@PathVariable Long hospital_seq, @RequestParam Long user_seq) {
//        HospitalDetailDto hospitalDetailDto = hospitalService.getHospitalLikeInfo(hospital_seq, user_seq);
//        return new ResponseEntity<>(hospitalDetailDto, HttpStatus.OK);
//    }

    @GetMapping("/detail/{hospital_seq}")
    @Operation(summary = "고객이 병원 상세 페이지를 조회한다. - 병원 상세 정보")
    public ResponseEntity<HospitalDetailDto> getHospitalBySeq(@PathVariable Long hospital_seq) {
        HospitalDetailDto hospitalDetailDto = hospitalService.getHospitalInfo(hospital_seq);
        return new ResponseEntity<>(hospitalDetailDto, HttpStatus.OK);
    }

    @GetMapping("/reviews/{hospital_seq}")
    @Operation(summary = "고객이 병원 상세 페이지를 조회한다. - 해당 병원 후기 목록")
    public ResponseEntity<List<ReviewListDisplay>> getHospitalReview(@PathVariable Long hospital_seq) {
        List<ReviewBoard> reviews = hospitalService.getReviewsByHospital(hospital_seq);
        List<ReviewListDisplay> reviewDisplay = new ArrayList<>();
        for(ReviewBoard r : reviews) {
            reviewDisplay.add(new ReviewListDisplay(r.getSeq(), r.getUser().getName(), r.getTitle(), r.getCnt(),
                    r.getRegdate(), r.getScore(), r.getDoctor().getDocInfoName(), r.getRegion(), r.getSurgery(), r.getHospital().getUser().getName(),
                    r.getExpectedPrice(), r.getSurgeryPrice()));
        }
        return new ResponseEntity<>(reviewDisplay, HttpStatus.OK);
    }

    @GetMapping("/doctors/{hospital_seq}")
    @Operation(summary = "고객이 병원 상세 페이지를 조회한다. - 해당 병원 의사 목록")
    public ResponseEntity<List<DoctorListDto>> getHospitalDoctors(@PathVariable Long hospital_seq) {
        List<Doctor> doctors = hospitalService.getHospitalDoctorList(hospital_seq);
        List<DoctorListDto> doctorDtoList = new ArrayList<>();
        for(Doctor d : doctors) {
            List<DoctorCategory> doctorCategories = doctorService.getCategory(d.getDocInfoSeq());
            List<CategoryDto> categoryDto = new ArrayList<>();
            for(DoctorCategory c : doctorCategories) {
                categoryDto.add(new CategoryDto(c.getPart()));
            }
            double avgScore = doctorService.getAvgScore(d.getDocInfoSeq());
            int cntReviews = doctorService.getCntReviews(d.getDocInfoSeq());
            doctorDtoList.add(new DoctorListDto(d.getDocInfoSeq(), d.getDocInfoName(), avgScore, cntReviews, categoryDto));
        }
        return new ResponseEntity<>(doctorDtoList, HttpStatus.OK);
    }


}
