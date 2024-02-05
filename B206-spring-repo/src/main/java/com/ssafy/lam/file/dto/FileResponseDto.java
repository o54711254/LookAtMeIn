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
    @JsonProperty("originalPath")
    private Resource originalPath;

    @Builder
    public FileResponseDto(Long fileSeq, Resource originalPath) {
        this.fileSeq = fileSeq;
        this.originalPath = originalPath;
    }
}
