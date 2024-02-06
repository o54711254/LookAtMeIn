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
    private HospitalDto hospital;

    private List<CategoryDto> categoryList;

    @Builder
    public HospitalRequestDto(HospitalDto hospitalDto, List<CategoryDto> categoryList) {
        this.hospital = hospitalDto;
        this.categoryList = categoryList;
    }
}