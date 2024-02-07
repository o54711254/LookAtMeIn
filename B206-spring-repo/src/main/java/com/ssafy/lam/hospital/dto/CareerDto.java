package com.ssafy.lam.hospital.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CareerDto {

    private Integer career_start;
    private Integer career_end;
    private String career_content;

    @Builder
    public CareerDto(Integer career_start, Integer career_end, String career_content) {
        this.career_start = career_start;
        this.career_end = career_end;
        this.career_content = career_content;
    }
}
