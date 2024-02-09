package com.ssafy.lam.requestboard.dto;

import com.ssafy.lam.requestboard.domain.Requestboard;
import com.ssafy.lam.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class RequestSaveDto {

    private Long seq;
    private String title;
    private String content;
    private Long userSeq; // User 엔티티의 사용자 이름을 가정
    private LocalDate regDate;
    private List<SurgeryDto> surgeries; // RequestSaveDto 내부에서 SurgeryDto 사용

    // Builder를 사용한 생성자
    @Builder
    public RequestSaveDto(Long seq, String title, String content, Long userSeq, LocalDate regDate, List<SurgeryDto> surgeries) {
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.userSeq = userSeq;
        this.regDate = regDate;
        this.surgeries = surgeries;
    }

    // 이 DTO를 Requestboard 엔티티로 변환
    public Requestboard toEntity(User user) {
        return Requestboard.builder()
                .title(title)
                .content(content)
                .user(user) // 이름만으로는 User 엔티티를 생성할 수 없으므로 User 엔티티가 여기에 전달되었다고 가정
                .requestCnt(surgeries.size()) // surgeries의 크기로 requestCnt 설정, 수술 개수를 반영한다고 가정
                .cnt(0) // 초기 카운트는 0으로 가정, 요구사항에 맞게 조정
                .isDeleted(false) // 새 요청은 삭제되지 않았다고 가정
                .regDate(regDate)
                .build();
    }
}