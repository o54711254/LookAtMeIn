package com.ssafy.lam.hospital.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class HospitalRequestDto {
    private HospitalDto hospital;

    private List<CategoryDto> categoryList;

    @Builder
    public HospitalRequestDto(HospitalDto hospitalDto, List<CategoryDto> categoryList) {
        this.hospital = hospitalDto;
        this.categoryList = categoryList;
    }
}