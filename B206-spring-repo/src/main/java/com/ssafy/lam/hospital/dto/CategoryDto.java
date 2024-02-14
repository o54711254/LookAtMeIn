package com.ssafy.lam.hospital.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CategoryDto {
    private String part;

    public CategoryDto(String part) {
        this.part = part;
    }
}