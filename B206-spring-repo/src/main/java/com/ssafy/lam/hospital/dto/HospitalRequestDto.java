package com.ssafy.lam.hospital.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class HospitalRequestDto {
    private HospitalDto hospitalDto;
    private List<CategoryDto> categoryList;

    @Builder
    public HospitalRequestDto(HospitalDto hospitalDto, List<CategoryDto> categoryList) {
        this.hospitalDto = hospitalDto;
        this.categoryList = categoryList;
    }
}