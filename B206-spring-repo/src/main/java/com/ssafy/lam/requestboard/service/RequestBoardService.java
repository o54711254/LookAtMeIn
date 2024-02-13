package com.ssafy.lam.requestboard.service;

import com.ssafy.lam.common.EncodeFile;
import com.ssafy.lam.config.MultipartConfig;
import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.customer.domain.CustomerRepository;
import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.requestboard.domain.*;
import com.ssafy.lam.requestboard.dto.*;
import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    private final CustomerRepository customerRepository;
    MultipartConfig multipartConfig = new MultipartConfig();
    private String uploadPath = multipartConfig.multipartConfigElement().getLocation();

    private Logger log = LoggerFactory.getLogger(RequestBoardService.class);

    //등록기능
    public Long saveRequestboard(RequestSaveDto requestSaveDto) {
        User user = userRepository.findByUserSeq(requestSaveDto.getUserSeq()).orElseThrow(() -> new RuntimeException("유저 없음"));
        Requestboard requestboard = Requestboard.builder()
                .user(user)
                .title(requestSaveDto.getTitle())
                .content(requestSaveDto.getContent())
                .requestCnt(0)
                .cnt(0)
                .isDeleted(false)
                .regDate(requestSaveDto.getRegDate())
                .build();


        requestboard = requestboardRepository.save(requestboard);
        List<Surgery> surgeries = new ArrayList<>();
        for (SurgeryDto surgeryDto : requestSaveDto.getSurgeries()) {
            Surgery surgery = Surgery.builder()
                    .requestboard(requestboard)
                    .part(surgeryDto.getPart())
                    .type(surgeryDto.getType())
                    .isDeleted(false)
                    .build();
            surgeries.add(surgery);
        }


        surgeryRepository.saveAll(surgeries);

        return requestboard.getSeq();
    }

    //조회기능
//    public List<RequestResponDto> findAllRequestbooard() {
//        List<Requestboard> requestboards = requestboardRepository.findByIsDeletedFalse();
//
//        return requestboards.stream().map(requestboard -> RequestResponDto.builder()
//                .seq(requestboard.getSeq())
//                .title(requestboard.getTitle())
//                .userName(requestboard.getUser().getName())
//                .regDate(requestboard.getRegDate())
//                .requestCnt(requestboard.getRequestCnt())
//                .cnt(requestboard.getCnt())
//                .isDeleted(requestboard.isDeleted())
//                .surgeries(requestboard.getSurgeries().stream().map(surgery -> SurgeryDto.builder()
//                        .seq(surgery.getSeq())
//                        .part(surgery.getPart())
//                        .type(surgery.getType())
//                        .regDate(surgery.getRegDate())
//                        .isDeleted(surgery.isDeleted())
//                        .build()
//                ).collect(Collectors.toList()))
//                .build()).collect(Collectors.toList());
//    }

    public List<RequestResponDto> findByUserUserSeqAndIsDeletedFalse(Long userSeq){
        List<Requestboard> requestboards = requestboardRepository.findByUserUserSeqAndIsDeletedFalse(userSeq);
        List<RequestResponDto> requestResponDtos = new ArrayList<>();

        for (Requestboard requestboard : requestboards) {
            Customer customer = customerRepository.findByUserUserSeq(requestboard.getUser().getUserSeq())
                    .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

            RequestResponDto requestResponDto = RequestResponDto.builder()
                    .seq(requestboard.getSeq())
                    .title(requestboard.getTitle())
                    .content(requestboard.getContent())
                    .userSeq(requestboard.getUser().getUserSeq())
                    .userName(requestboard.getUser().getName())
                    .regDate(requestboard.getRegDate())
                    .requestCnt(requestboard.getRequestCnt())
                    .cnt(requestboard.getCnt())
                    .isDeleted(requestboard.isDeleted())
                    .surgeries(requestboard.getSurgeries().stream()
                            .map(surgery -> SurgeryDto.builder()
                                    .seq(surgery.getSeq())
                                    .part(surgery.getPart())
                                    .type(surgery.getType())
                                    .regDate(surgery.getRegDate())
                                    .isDeleted(surgery.isDeleted())
                                    .build())
                            .collect(Collectors.toList()))
                    .build();
            if (customer.getProfile() != null) {
                UploadFile customerProfile = customer.getProfile();
                Path path = Paths.get(uploadPath + "/" + customerProfile.getName());
                try {
                    String customerProfileBase64 = EncodeFile.encodeFileToBase64(path);
                    String customerProfileType = customerProfile.getType();
                    requestResponDto.setCustomerProfileBase64(customerProfileBase64);
                    requestResponDto.setCustomerProfileType(customerProfileType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            requestResponDtos.add(requestResponDto);
        }
        return requestResponDtos;
    }

    public List<RequestResponDto> findAllRequestboard() {
        List<Requestboard> requestboards = requestboardRepository.findByIsDeletedFalse();
        for (Requestboard r : requestboards) {
            System.out.println(r.getUser().getUserSeq());
        }

        return requestboards.stream()
                .map(this::toRequestResponDto)
                .collect(Collectors.toList());
    }

    public RequestResponDto toRequestResponDto(Requestboard requestboard) {
        Customer customer = customerRepository.findByUserUserSeq(requestboard.getUser().getUserSeq())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        RequestResponDto requestResponDto = RequestResponDto.builder()
                .seq(requestboard.getSeq())
                .title(requestboard.getTitle())
                .content(requestboard.getContent())
                .userSeq(requestboard.getUser().getUserSeq())
                .userName(requestboard.getUser().getName())
                .regDate(requestboard.getRegDate())
                .requestCnt(requestboard.getRequestCnt())
                .cnt(requestboard.getCnt())
                .isDeleted(requestboard.isDeleted())
                .surgeries(requestboard.getSurgeries().stream()
                        .map(surgery -> SurgeryDto.builder()
                                .seq(surgery.getSeq())
                                .part(surgery.getPart())
                                .type(surgery.getType())
                                .regDate(surgery.getRegDate())
                                .isDeleted(surgery.isDeleted())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        if (customer.getProfile() != null) {
            UploadFile customerProfile = customer.getProfile();
            Path path = Paths.get(uploadPath + "/" + customerProfile.getName());
            try {
                String customerProfileBase64 = EncodeFile.encodeFileToBase64(path);
                String customerProfileType = customerProfile.getType();
                requestResponDto.setCustomerProfileBase64(customerProfileBase64);
                requestResponDto.setCustomerProfileType(customerProfileType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return requestResponDto;
    }

    //상세조회
    @Transactional
    public RequestResponDto findRequestboard(Long requestSeq) {

        Optional<Requestboard> requestboardOptional = requestboardRepository.findBySeqAndIsDeletedFalse(requestSeq);

        Customer customer = customerRepository.findByUserUserSeq(requestboardOptional.get().getUser().getUserSeq()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        if (requestboardOptional.isPresent()) {
            Requestboard requestboard = requestboardRepository.findById(requestSeq).get();

            requestboard.setCnt(requestboard.getCnt() + 1);
            requestboardRepository.save(requestboard);

            RequestResponDto requestResponDto = RequestResponDto.builder()
                    .seq(requestboard.getSeq())
                    .title(requestboard.getTitle())
                    .content(requestboard.getContent())
                    .userSeq(requestboard.getUser().getUserSeq())
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

            if (customer.getProfile() != null) {
                UploadFile customerProfile = customer.getProfile();
                Path path = Paths.get(uploadPath + "/" + customerProfile.getName());
                try {
                    String customerProfileBase64 = EncodeFile.encodeFileToBase64(path);
                    String customerProfileType = customerProfile.getType();
                    requestResponDto.setCustomerProfileBase64(customerProfileBase64);
                    requestResponDto.setCustomerProfileType(customerProfileType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return requestResponDto;
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

        log.info("userSeq : {}", responseDto.getUserSeq());

        User user = userRepository.findById(responseDto.getUserSeq())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없음 : " + responseDto.getUserSeq()));

        if (user.getUserType().equals("HOSPITAL")) {
            Response response = Response.builder()
                    .requestboard(requestboard)
                    .user(user)
                    .message(responseDto.getMessage())
                    .build();
            responseRepository.save(response);
            createNotification(requestboard.getUser(), user, response.getMessage());

        } else {
            new IllegalArgumentException("병원만 제안 가능 : " + responseDto.getUserSeq());
        }
    }

    public void createNotification(User recipient, User sender, String message) {
        Notification notification = new Notification(sender.getName(), recipient, message, false);
        notificationRepository.save(notification);
    }

    public List<NotificationDto> findAllNotificationsByUser(Long userSeq) {
        List<Notification> notifications = notificationRepository.findAllByRecipientUserSeq(userSeq);

        return notifications.stream()
                .map(notification -> NotificationDto.builder()
                        .seq(notification.getId())
                        .hospitalName(notification.getSender())
                        .message(notification.getMessage())
                        .isRead(notification.isRead())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ResponseDto> findAllresponse(long userSeq) {
        List<Response> responses = responseRepository.findAllByUserUserSeq(userSeq);
        List<ResponseDto> responseDtos = responses.stream()
                .map(response -> new ResponseDto(response.getId(), response.getUser().getUserSeq(), response.getUser().getName(), response.getMessage()))
                .collect(Collectors.toList());

        return responseDtos;
    }
}
