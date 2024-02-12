package com.ssafy.lam.requestboard.dto;

import com.ssafy.lam.requestboard.domain.Requestboard;
import com.ssafy.lam.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@ToString
public class RequestSaveDto {

    private Long seq;
    private String title;
    private String content;
    private Long userSeq;
    private LocalDate regDate;
    private List<SurgeryDto> surgeries;

    @Builder
    public RequestSaveDto(Long seq, String title, String content, Long userSeq, LocalDate regDate, List<SurgeryDto> surgeries) {
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.userSeq = userSeq;
        this.regDate = regDate;
        this.surgeries = surgeries;
    }

    public Requestboard toEntity(User user) {
        return Requestboard.builder()
                .title(title)
                .content(content)
                .user(user)
                .requestCnt(0)
                .cnt(0)
                .isDeleted(false)
                .regDate(regDate)
                .build();
    }
}