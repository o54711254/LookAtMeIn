package com.ssafy.lam.requestboard.service;

import com.ssafy.lam.requestboard.domain.Requestboard;
import com.ssafy.lam.requestboard.domain.RequestboardRepository;
import com.ssafy.lam.requestboard.domain.Surgery;
import com.ssafy.lam.requestboard.domain.SurgeryRepository;
import com.ssafy.lam.requestboard.dto.RequestDto;
import com.ssafy.lam.requestboard.dto.RequestSaveDto;
import com.ssafy.lam.requestboard.dto.RequestUpdateDto;
import com.ssafy.lam.requestboard.dto.SurgeryDto;
import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestBoardService {
    private final RequestboardRepository requestboardRepository;
    private final UserRepository userRepository;
    private final SurgeryRepository surgeryRepository;

    //등록기능
    public Long saveRequestboard(RequestSaveDto requestSaveDto) {
        User user = userRepository.findByUserSeq(requestSaveDto.getUserSeq()).orElseThrow(() -> new RuntimeException("유저 없음"));
        Requestboard requestboard = requestboardRepository.save(requestSaveDto.toEntity(user));

        List<Surgery> surgeries = requestSaveDto.getSurgeries().stream()
                .map(surgeryDto -> surgeryDto.toEntity(requestboard))
                .collect(Collectors.toList());
        surgeryRepository.saveAll(surgeries);

        return requestboard.getSeq();
    }

    //조회기능
    public List<RequestDto> findAllRequestbooard() {
        List<Requestboard> requestboards = requestboardRepository.findByIsDeletedFalse();

        return requestboards.stream().map(requestboard -> RequestDto.builder()
                .seq(requestboard.getSeq())
                .title(requestboard.getTitle())
                .userName(requestboard.getUser().getName())
                .regDate(requestboard.getRegDate())
                .requestCnt(requestboard.getRequestCnt())
                .cnt(requestboard.getCnt())
                .isDeleted(requestboard.isDeleted())
                .surgeries(requestboard.getSurgeries().stream().map(surgery -> SurgeryDto.builder()
                        .seq(surgery.getSeq())
                        .part(surgery.getPart())
                        .type(surgery.getType())
                        .regDate(surgery.getRegDate())
                        .isDeleted(surgery.isDeleted())
                        .build()
                ).collect(Collectors.toList()))
                .build()).collect(Collectors.toList());
    }

    //상세조회
    @Transactional
    public RequestDto finRequestboard(Long requestSeq) {
        // 여기사이에 조회수 들어가야 함
        Optional<Requestboard> requestboardOptional = requestboardRepository.findBySeqAndIsDeletedFalse(requestSeq);
        if (requestboardOptional.isPresent()) {
            Requestboard requestboard = requestboardRepository.findById(requestSeq).get();

            requestboard.setCnt(requestboard.getCnt() + 1);
            requestboardRepository.save(requestboard);

            RequestDto requestDto = RequestDto.builder()
                    .seq(requestboard.getSeq())
                    .title(requestboard.getTitle())
                    .userName(requestboard.getUser().getUsername())
                    .regDate(requestboard.getRegDate())
                    .requestCnt(requestboard.getRequestCnt())
                    .cnt(requestboard.getCnt())
                    .isDeleted(requestboard.isDeleted())
                    .surgeries(requestboard.getSurgeries().stream().map(surgery -> SurgeryDto.builder()
                                    .seq(surgery.getSeq())
                                    .part(surgery.getPart())
                                    .type(surgery.getType())
                                    .regDate(surgery.getRegDate())
                                    .isDeleted(surgery.isDeleted())
                                    .build())
                            .collect(Collectors.toList()))
                    .build();

            return requestDto;
        }
        return null;
    }

    //삭제기능
    @Transactional
    public void deleteRequestboard(Long requestSeq) {
        Requestboard requestboard = requestboardRepository.findById(requestSeq)
                .orElseThrow(() -> new IllegalArgumentException("없는 게시물입니다 :" + requestSeq));

        requestboard.setDeleted(true);
        requestboardRepository.save(requestboard);
    }
//수정기능
@Transactional
public RequestUpdateDto updateRequestboard(Long requestSeq, RequestUpdateDto requestUpdateDto) {
    Requestboard requestboard = requestboardRepository.findById(requestSeq)
            .orElseThrow(() -> new IllegalArgumentException("없는 게시물입니다 : " + requestSeq));

    if (requestUpdateDto.getTitle() != null) requestboard.setTitle(requestUpdateDto.getTitle());
    if (requestUpdateDto.getContent() != null) requestboard.setContent(requestUpdateDto.getContent());
    requestboard.setDeleted(requestUpdateDto.isDeleted());

    Requestboard updatedRequestboard = requestboardRepository.save(requestboard);

    return RequestUpdateDto.builder()
            .seq(requestUpdateDto.getSeq())
            .title(requestUpdateDto.getTitle())
            .content(requestboard.getContent())
            .isDeleted(requestUpdateDto.isDeleted())
            .surgeries(requestboard.getSurgeries().stream().map(surgery -> SurgeryDto.builder()
                            .seq(surgery.getSeq())
                            .part(surgery.getPart())
                            .type(surgery.getType())
                            .regDate(surgery.getRegDate())
                            .isDeleted(surgery.isDeleted())
                            .build())
                    .collect(Collectors.toList()))
            .build();
}

    private RequestDto convertToDto(Requestboard requestboard) {
        // Requestboard 엔티티를 RequestDto로 변환하는 메서드 구현
        return RequestDto.builder()
                .seq(requestboard.getSeq())
                .title(requestboard.getTitle())
                .userName(requestboard.getUser().getName()) // userName을 적절히 처리해야 합니다.
                .regDate(requestboard.getRegDate())
                .requestCnt(requestboard.getRequestCnt())
                .cnt(requestboard.getCnt())
                .isDeleted(requestboard.isDeleted())
                // Surgeries 리스트 변환 로직은 생략
                .build();
    }

}
