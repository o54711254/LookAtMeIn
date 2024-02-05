package com.ssafy.lam.hospital.service;

import com.ssafy.lam.hospital.domain.Category;
import com.ssafy.lam.hospital.domain.CategoryRepository;
import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.domain.HospitalRepository;

import com.ssafy.lam.hospital.dto.CategoryDto;
import com.ssafy.lam.hospital.dto.HospitalDto;
import com.ssafy.lam.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService{
    private final HospitalRepository hospitalRepostiory;
    private final UserService userService;
    private final CategoryRepository categoryRepository;

    private Logger log = LoggerFactory.getLogger(HospitalServiceImpl.class);

    @Override
    public Hospital createHospital(HospitalDto hospitalDto, List<CategoryDto> categoryDto) {
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
        hospital =  hospitalRepostiory.save(hospital);
        for(CategoryDto category : categoryDto){
            log.info("category : {}", category);
            Category categoryEntity = Category.builder()
                    .part(category.getPart())
                    .hospital(hospital)
                    .build();

            categoryRepository.save(categoryEntity);

        }

        return hospital;


    }


}
