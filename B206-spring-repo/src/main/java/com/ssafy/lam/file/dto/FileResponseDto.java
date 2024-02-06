package com.ssafy.lam.file.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.Resource;

@Getter
@Setter
@NoArgsConstructor
public class FileResponseDto {
    private Long fileSeq;
    private String name;
    private String type;


    @Builder

    public FileResponseDto(Long fileSeq, String name, String type) {
        this.fileSeq = fileSeq;
        this.name = name;
        this.type = type;
    }
}
