package com.ssafy.lam.requestboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
public class RequestDto {
    private Long seq;
    private String title;
    private String content;
    private Long userSeq;
    private String userName;
    private LocalDate regDate;
    private int requestCnt;
    private int cnt;
    private boolean isDeleted;
    private List<SurgeryDto> surgeries;
    private MultipartFile uploadFile;


    @Builder
    public RequestDto(Long seq, String title,String content, Long userSeq, String userName, LocalDate regDate, int requestCnt, int cnt, List<SurgeryDto> surgeries, boolean isDeleted) {
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.userSeq = userSeq;
        this.userName = userName;
        this.regDate = regDate;
        this.requestCnt = requestCnt;
        this.cnt = cnt;
        this.surgeries = surgeries;
        this.isDeleted = isDeleted;
    }
}
