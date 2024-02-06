package com.ssafy.lam.hospital.dto;

import com.ssafy.lam.hospital.domain.Career;
import com.ssafy.lam.hospital.domain.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DoctorDto {

    private Long doc_info_seq;
    private String doc_info_name;
    private CategoryDto[] doc_info_category;
    private Career[] doc_info_career;


}
