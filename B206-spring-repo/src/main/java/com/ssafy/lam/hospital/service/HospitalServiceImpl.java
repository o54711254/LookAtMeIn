package com.ssafy.lam.hospital.service;

import com.ssafy.lam.hospital.domain.Doctor;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.domain.HospitalRepository;
import com.ssafy.lam.hospital.dto.CategoryDto;
import com.ssafy.lam.hospital.dto.HospitalDetailDto;
import com.ssafy.lam.hospital.dto.HospitalDto;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.user.domain.UserRepository;
import com.ssafy.lam.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    private Logger log = LoggerFactory.getLogger(HospitalServiceImpl.class);

    @Override
    public com.ssafy.lam.hospital.domain.Hospital createHospital(HospitalDto hospitalDto, List<CategoryDto> categoryDto) {
        log.info("createHospital : {}", hospitalDto);
        List<String> roles = new ArrayList<>();
        roles.add("HOSPITAL");
        User user = User.builder()
                .name(hospitalDto.getHospitalInfo_name())
                .userId(hospitalDto.getHospitalInfo_id())
                .password(hospitalDto.getHospitalInfo_password())
                .userType("HOSPITAL")
                .roles(roles)
                .build();

        userService.createUser(user);

        com.ssafy.lam.hospital.domain.Hospital hospital = com.ssafy.lam.hospital.domain.Hospital.builder()
                .user(user)
                .tel(hospitalDto.getHospitalInfo_phoneNumber())
                .address(hospitalDto.getHospitalInfo_address())
                .intro(hospitalDto.getHospitalInfo_introduce())
                .email(hospitalDto.getHospitalInfo_email())
                .openTime(hospitalDto.getHospitalInfo_open())
                .closeTime(hospitalDto.getHospitalInfo_close())
                .url(hospitalDto.getHospitalInfo_url())
                .build();

        return hospitalRepository.save(hospital);
    }

    @Override
    public HospitalDto getHospital(long userId) {
        Optional<Hospital> hospitalOptional = hospitalRepository.findByUserUserSeq(userId);
        if (hospitalOptional.isPresent()) {
            Hospital hospital = hospitalOptional.get();

            HospitalDto dto = HospitalDto.builder()
                    .hospitalInfo_id(hospital.getUser().getUserId())
                    .hospitalInfo_password(hospital.getUser().getPassword())
                    .hospitalInfo_name(hospital.getUser().getName())
                    .hospitalInfo_phoneNumber(hospital.getTel())
                    .hospitalInfo_introduce(hospital.getIntro())
                    .hospitalInfo_address(hospital.getAddress())
                    .hospitalInfo_open(hospital.getOpenTime())
                    .hospitalInfo_close(hospital.getCloseTime())
                    .hospitalInfo_url(hospital.getUrl())
                    .build();
            return dto;
        } else {
            return null;
        }
    }

    @Override
    public Hospital updateHospital(long userSeq, HospitalDto hospitalDto) {
        User user = userRepository.findById(userSeq).get();
        Hospital hospital = hospitalRepository.findByUserUserSeq(userSeq).get();

        user.setPassword(hospitalDto.getHospitalInfo_password());
        user.setName(hospitalDto.getHospitalInfo_name());
        hospital.setTel(hospitalDto.getHospitalInfo_phoneNumber());
        hospital.setEmail(hospitalDto.getHospitalInfo_email());
        hospital.setOpenTime(hospitalDto.getHospitalInfo_open());
        hospital.setCloseTime(hospitalDto.getHospitalInfo_close());
        hospital.setAddress(hospitalDto.getHospitalInfo_address());
        hospital.setUrl(hospitalDto.getHospitalInfo_url());

        userRepository.save(user);
        return hospitalRepository.save(hospital);
    }

    ////////////

    @Override
    public HospitalDetailDto getHospitalInfo(Long hospitalSeq) { // 고객이 병원 페이지 조회
        Optional<Hospital> hospitalOptional = hospitalRepository.findById(hospitalSeq);
        if (hospitalOptional.isPresent()) {
            Hospital hospital = hospitalOptional.get();
            HospitalDetailDto hospitalDetailDto = HospitalDetailDto.builder()
                    .hospitalInfo_seq(hospitalSeq)
                    .hospitalInfo_name(hospital.getUser().getName())
                    .hospitalInfo_phoneNumber(hospital.getTel())
                    .hospitalInfo_introduce(hospital.getIntro())
                    .hospitalInfo_address(hospital.getAddress())
                    .hospitalInfo_open(hospital.getOpenTime())
                    .hospitalInfo_close(hospital.getCloseTime())
                    .hospitalInfo_url(hospital.getUrl())
                    .userSeq(hospital.getUser().getUserSeq())
                    .build();
            return hospitalDetailDto;
        } else {
            return null;
        }
    }

    @Override
    public List<ReviewBoard> getReviewsByHospital(Long hospitalSeq) {
        List<ReviewBoard> reviews = hospitalRepository.findReviewsByHospitalSeq(hospitalSeq);
        return reviews;
    }

    @Override
    public List<Doctor> getHospitalDoctorList(Long hospitalSeq) {
        List<Doctor> doctorList = hospitalRepository.findDoctorByHospitalSeq(hospitalSeq).orElse(null);
        return doctorList;
    }

}
