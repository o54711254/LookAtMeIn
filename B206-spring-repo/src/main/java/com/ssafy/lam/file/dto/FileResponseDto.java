package com.ssafy.lam.file.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileResponseDto {
    private Long fileSeq;
    private String name;
    private String type;

    private String base64;

    @Builder
    public FileResponseDto(Long fileSeq, String name, String type, String base64) {
        this.fileSeq = fileSeq;
        this.name = name;
        this.type = type;
        this.base64 = base64;
    }
}
