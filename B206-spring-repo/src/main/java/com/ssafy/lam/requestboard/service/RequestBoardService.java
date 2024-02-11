package com.ssafy.lam.requestboard.service;

import com.ssafy.lam.requestboard.domain.*;
import com.ssafy.lam.requestboard.dto.*;
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
    private final ResponseRepository responseRepository;
    private final NotificationRepository notificationRepository;

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

        requestboardRepository.save(requestboard);

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

    public void createResponse(Long requestSeq, ResponseDto responseDto) {
        Requestboard requestboard = requestboardRepository.findById(requestSeq)
                .orElseThrow(() -> new IllegalArgumentException("요청게시판을 찾을 수 없음 : " + requestSeq));
        User user = userRepository.findById(responseDto.getUserSeq())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없음 : " + responseDto.getUserSeq()));

        Response response = Response.builder()
                .requestboard(requestboard)
                .user(user)
                .message(responseDto.getMessage())
                .build();
        responseRepository.save(response);

        // 알림 생성 로직 호출
        createNotification(requestboard.getUser(), user, response.getMessage());
    }

    public void createNotification(User recipient, User sender, String message) {
        String notificationMessage = String.format("%s 제안을 보낸 병원 : %s", sender.getName(), message);
        Notification notification = new Notification(recipient, notificationMessage, false);
        notificationRepository.save(notification);
    }

    public List<ResponseDto> findAllresponse(long userSeq){
        List<Response> responses = responseRepository.findAllByUserUserSeq(userSeq);
        for(Response r : responses){
            System.out.println("메세지"+r.getMessage());
        }
        List<ResponseDto> responseDtos = responses.stream()
                .map(response -> new ResponseDto(response.getId(), response.getUser().getUserSeq(), response.getUser().getName(), response.getMessage()))
                .collect(Collectors.toList());
        for(ResponseDto r : responseDtos){
            System.out.println(r.getUserSeq()+" "+r.getMessage());
        }
        return responseDtos;
    }
}
