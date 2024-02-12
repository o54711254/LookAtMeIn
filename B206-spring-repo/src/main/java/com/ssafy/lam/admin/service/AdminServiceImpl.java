package com.ssafy.lam.admin.service;

import com.ssafy.lam.common.EncodeFile;
import com.ssafy.lam.config.MultipartConfig;
import com.ssafy.lam.freeboard.domain.FreeboardRepository;
import com.ssafy.lam.freeboard.dto.FreeboardAdminDto;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.domain.HospitalRepository;
import com.ssafy.lam.hospital.dto.HospitalAdminDto;
import com.ssafy.lam.reviewBoard.domain.ReviewBoardRepository;
import com.ssafy.lam.reviewBoard.dto.ReviewBoardAdminDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final FreeboardRepository freeboardRepository;
    private final ReviewBoardRepository reviewBoardRepository;
    private final HospitalRepository hospitalRepository;
    MultipartConfig multipartConfig = new MultipartConfig();
    private String uploadPath = multipartConfig.multipartConfigElement().getLocation();

    @Override
    public List<FreeboardAdminDto> findComplainedAndNotDeletedFreeboards() {

        return freeboardRepository.findByComplainTrueAndIsDeletedFalse()
                .stream()
                .map(freeboard -> FreeboardAdminDto.builder()
                        .freeboardSeq(freeboard.getFreeboardSeq())
                        .userId(freeboard.getUser().getUserId())
                        .userName(freeboard.getUser().getName())
                        .freeboardTitle(freeboard.getTitle())
                        .freeboardContent(freeboard.getContent())
                        .complain(freeboard.isComplain())
                        .isDeleted(freeboard.isDeleted())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewBoardAdminDto> findComplainedAndNotDeletedReviewBoards() {
        return reviewBoardRepository.findByComplainTrueAndIsdeletedFalse()
                .stream()
                .map(reviewBoard -> ReviewBoardAdminDto.builder()
                        .reviewBoard_seq(reviewBoard.getSeq())
                        .reviewBoard_title(reviewBoard.getTitle())
                        .reviewBoard_content(reviewBoard.getContent())
                        .customer_name(reviewBoard.getUser().getName())
                        .reviewBoard_doctor(reviewBoard.getDoctor().getDocInfoName())
                        .reviewBoard_region(reviewBoard.getRegion())
                        .score(reviewBoard.getScore())
                        .reviewBoard_hospital(reviewBoard.getHospital().getUser().getName())
                        .reviewBoard_expected_price(reviewBoard.getExpectedPrice())
                        .reviewBoard_surgery_price(reviewBoard.getSurgeryPrice())
                        .regdate(reviewBoard.getRegdate())
                        .complain(reviewBoard.isComplain())
                        .isdeleted(reviewBoard.isIsdeleted())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<HospitalAdminDto> findUnapprovedHospitals() {

        List<HospitalAdminDto> hospitalAdminDtoList = new ArrayList<>();
        List<Hospital> hospitals = hospitalRepository.findAllByIsApprovedFalse();

            for(Hospital hospital : hospitals){
                try {
                    HospitalAdminDto hospitalAdminDto = HospitalAdminDto.builder()
                            .hospitalSeq(hospital.getHospitalSeq())
                            .userSeq(hospital.getUser().getUserSeq())
                            .hospitalInfo_id(hospital.getUser().getUserId())
                            .hospitalInfo_name(hospital.getUser().getName())
                            .isApproved(hospital.isApproved())
                            .build();

                    if(hospital.getRegistrationFile() != null){
                        Path path = Paths.get(uploadPath + "/" + hospital.getRegistrationFile().getName());
                        String registrationFileBase64 = EncodeFile.encodeFileToBase64(path);

                        hospitalAdminDto.setRegistrationFileBase64(registrationFileBase64);
                    }


                    hospitalAdminDtoList.add(hospitalAdminDto);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return hospitalAdminDtoList;
    }

    @Override
    public List<HospitalAdminDto> findApprovedHospitals() {
//

        List<HospitalAdminDto> hospitalAdminDtoList = new ArrayList<>();
        List<Hospital> hospitals = hospitalRepository.findByIsApprovedTrue();

        for(Hospital hospital : hospitals){
            try {
                HospitalAdminDto hospitalAdminDto = HospitalAdminDto.builder()
                        .hospitalSeq(hospital.getHospitalSeq())
                        .userSeq(hospital.getUser().getUserSeq())
                        .hospitalInfo_id(hospital.getUser().getUserId())
                        .hospitalInfo_name(hospital.getUser().getName())
                        .isApproved(hospital.isApproved())
                        .build();

                if(hospital.getRegistrationFile() != null){
                    Path path = Paths.get(uploadPath + "/" + hospital.getRegistrationFile().getName());
                    String registrationFileBase64 = EncodeFile.encodeFileToBase64(path);
                    String type = hospital.getRegistrationFile().getType();
                    hospitalAdminDto.setRegistrationFileBase64(registrationFileBase64);

                }


                hospitalAdminDtoList.add(hospitalAdminDto);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return hospitalAdminDtoList;
    }

    @Override
    @Transactional
    public boolean approveHospital(Long userSeq) {
        Hospital hospital = hospitalRepository.findByUserUserSeq(userSeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 병원이 존재하지 않습니다. ID: " + userSeq));
        hospital.approve();
        return true;
    }

}
