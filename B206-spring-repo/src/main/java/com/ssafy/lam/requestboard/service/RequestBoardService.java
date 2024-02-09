package com.ssafy.lam.requestboard.service;

import com.ssafy.lam.requestboard.domain.Requestboard;
import com.ssafy.lam.requestboard.domain.RequestboardRepository;
import com.ssafy.lam.requestboard.domain.Surgery;
import com.ssafy.lam.requestboard.domain.SurgeryRepository;
import com.ssafy.lam.requestboard.dto.RequestDto;
import com.ssafy.lam.requestboard.dto.RequestSaveDto;
import com.ssafy.lam.requestboard.dto.SurgeryDto;
import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
        List<Requestboard> requestboards = requestboardRepository.findAll();

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

//디테일

//삭제기능

//수정기능

}
