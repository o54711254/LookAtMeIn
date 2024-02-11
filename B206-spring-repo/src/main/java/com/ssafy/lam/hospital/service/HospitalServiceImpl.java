package com.ssafy.lam.hospital.service;

import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.file.service.UploadFileService;
import com.ssafy.lam.hospital.domain.*;
import com.ssafy.lam.hospital.dto.*;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.user.domain.UserRepository;
import com.ssafy.lam.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final DoctorRepository doctorRepository;
    private final CareerRepository careerRepository;
    private final HospitalCategoryRepository hospitalCategoryRepository;
    private final UploadFileService uploadFileService;

    private final DoctorCategoryRepository doctorCategoryRepository;

    private Logger log = LoggerFactory.getLogger(HospitalServiceImpl.class);

    @Override
    public Hospital createHospital(HospitalDto hospitalDto, List<CategoryDto> categoryDto, MultipartFile registrationFile) {
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

        Hospital hospital = Hospital.builder()
                .user(user)
                .tel(hospitalDto.getHospitalInfo_phoneNumber())
                .address(hospitalDto.getHospitalInfo_address())
                .intro(hospitalDto.getHospitalInfo_introduce())
                .email(hospitalDto.getHospitalInfo_email())
                .openTime(hospitalDto.getHospitalInfo_open())
                .closeTime(hospitalDto.getHospitalInfo_close())
                .url(hospitalDto.getHospitalInfo_url())
                .build();
        UploadFile uploadFile = uploadFileService.store(registrationFile);
        hospital.setRegistrationFile(uploadFile);


        hospital = hospitalRepository.save(hospital);
        for (CategoryDto category : categoryDto) {
            log.info("category : {}", category);
            HospitalCategory hospitalCategoryEntity = HospitalCategory.builder()
                    .part(category.getPart())
                    .hospital(hospital)
                    .build();
            hospitalCategoryRepository.save(hospitalCategoryEntity);
        }

        return hospital;
    }

    @Override
    public HospitalDto getHospital(long userId) {
        Optional<Hospital> hospitalOptional = hospitalRepository.findById(userId);
        if (hospitalOptional.isPresent()) {
            com.ssafy.lam.hospital.domain.Hospital hospital = hospitalOptional.get();

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
    public List<Hospital> getAllHospitalInfo() {
        return hospitalRepository.findByIsApprovedTrue();
    }

    @Override
    public void createDoctor(Long hospitalSeq, DoctorDto doctorDto, List<CategoryDto> categoryDtoList, List<CareerDto> careerDtoList) {
        Hospital hospital = Hospital.builder().hospitalSeq(hospitalSeq).build();
        Doctor doctor = Doctor.builder().docInfoSeq(doctorDto.getDoc_info_seq()).docInfoName(doctorDto.getDoc_info_name())
                .hospital(hospital).build();
        doctorRepository.save(doctor);
        for(CategoryDto c : categoryDtoList) {
            DoctorCategory doctorCategory = DoctorCategory.builder()
                    .part(c.getPart())
                    .doctor(doctor)
                    .build();
            doctorCategoryRepository.save(doctorCategory);
        }
        for(CareerDto c : careerDtoList) {
            Career career = Career.builder().careerStart(c.getCareer_start()).careerEnd(c.getCareer_end())
                    .careerContent(c.getCareer_content()).doctor(doctor).build();
            careerRepository.save(career);
        }
    }

    @Override
    public HospitalDetailDto getHospitalInfo(Long hospitalSeq) { // 고객이 병원 페이지 조회
        Optional<Hospital> hospitalOptional = hospitalRepository.findById(hospitalSeq);
        if (hospitalOptional.isPresent()) {
            Hospital hospital = hospitalOptional.get();
            double avgScore = hospitalRepository.findAvgByHospitalSeq(hospitalSeq).orElse(0.0);
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
                    .hospitalInfo_avgScore(avgScore)
                    .hospitalInfo_cntReviews(hospitalRepository.countByHospitalSeq(hospitalSeq))
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
