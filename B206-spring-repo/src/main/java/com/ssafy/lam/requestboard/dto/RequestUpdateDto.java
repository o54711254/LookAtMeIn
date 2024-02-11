package com.ssafy.lam.requestboard.dto;

import com.ssafy.lam.requestboard.domain.Requestboard;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class RequestUpdateDto {
    private Long seq;
    private String title;
    private String content;

    private boolean isDeleted;
    private List<SurgeryDto> surgeries;


    @Builder
    public RequestUpdateDto(Long seq, String title, String content, boolean isDeleted, List<SurgeryDto> surgeries) {
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.isDeleted = isDeleted;
        this.surgeries = surgeries;
    }

    public Requestboard toEntity(){
        return Requestboard.builder()
                .title(title)
                .content(content)
                .isDeleted(isDeleted)
                .build();
    }
}
