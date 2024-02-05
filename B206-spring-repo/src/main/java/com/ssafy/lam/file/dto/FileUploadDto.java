package com.ssafy.lam.file.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FileUploadDto {

    private long seq; // 이미지 번호
    private MultipartFile image; // 입력받은 파일 - 일단 1개만
    private String originName; // 원본 이미지 파일명
    private String saveName; // 저장 파일명

    @Builder
    public FileUploadDto(String originName, String saveName) {
        this.originName = originName;
        this.saveName = saveName;
    }

//    public ImageUploadDto toEntity() {
//        return ImageUploadDto.builder()
//                .originName(originName)
//                .saveName(saveName)
//                .build();
//    }

}
