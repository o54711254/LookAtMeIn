package com.ssafy.lam.requestboard.dto;

import com.ssafy.lam.requestboard.domain.Requestboard;
import com.ssafy.lam.requestboard.domain.Surgery;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

// 수술 관련 정보를 다루기 위해 RequestSaveDto 내부에 중첩된 SurgeryDto 클래스 사용
@Getter
@Builder
public class SurgeryDto {
    private Long seq;
    private String part;
    private int type;
    private LocalDate regDate;
    private boolean isDeleted;

    // 이 DTO를 Surgery 엔티티로 변환
    public Surgery toEntity(Requestboard requestboard) {
        return Surgery.builder()
                .seq(seq) // 새 엔티티에 대해 일반적으로는 시퀀스를 유지하지 않지만 여기서는 유지한다고 가정
                .part(part)
                .type(type)
                .regDate(regDate)
                .isDeleted(isDeleted)
                .requestboard(requestboard)
                .build();
    }
}