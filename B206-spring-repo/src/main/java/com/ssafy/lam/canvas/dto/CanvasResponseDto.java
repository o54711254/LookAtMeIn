package com.ssafy.lam.canvas.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CanvasResponseDto {
    private String beforeImgBase64;
    private String afterImgBase64;

}
