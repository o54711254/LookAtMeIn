package com.ssafy.lam.search.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.lam.freeboard.domain.Freeboard;
import com.ssafy.lam.freeboard.domain.QFreeboard;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.domain.HospitalRepository;
import com.ssafy.lam.hospital.domain.QHospital;
import com.ssafy.lam.reviewBoard.domain.QReviewBoard;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import com.ssafy.lam.reviewBoard.domain.ReviewBoardRepository;
import com.ssafy.lam.search.dto.FreeboardDto;
import com.ssafy.lam.search.dto.HospitalDto;
import com.ssafy.lam.search.dto.ReviewBoardDto;
import com.ssafy.lam.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final JPAQueryFactory queryFactory;
    private final HospitalRepository hospitalRepository;
    private final ReviewBoardRepository reviewBoardRepository;
    private final UserRepository userRepository;

    public List<?> search(String keyword, String category) {
        if ("hospital".equals(category)) {
            return searchInHospital(keyword);
        } else if ("freeboard".equals(category)) {
            return searchInFreeboard(keyword);
        } else if ("reviewBoard".equals(category)) {
            return searchInReviewBoard(keyword);
        } else {
            List<Object> results = new ArrayList<>();
            results.addAll(searchInHospital(keyword));
            results.addAll(searchInFreeboard(keyword));
            results.addAll(searchInReviewBoard(keyword));
            return results;
        }
    }

    public List<HospitalDto> searchInHospital(String keyword) {
        QHospital hospital = QHospital.hospital;
        List<Hospital> results = queryFactory.selectFrom(hospital)
                .where(hospital.user.name.contains(keyword)
                        .or(hospital.address.contains(keyword))
                        .or(hospital.email.contains(keyword)))
                .fetch();

        return results.stream().map(result -> HospitalDto.builder()
                .hospitalInfo_seq(result.getHospitalSeq())
                .hospitalInfo_name(result.getUser().getName())
                .hospitalInfo_phoneNumber(result.getTel())
                .hospitalInfo_introduce(result.getIntro())
                .hospitalInfo_address(result.getAddress())
                .hospitalInfo_open(result.getOpenTime())
                .hospitalInfo_close(result.getCloseTime())
                .hospitalInfo_avgScore(hospitalRepository.findAvgByHospitalSeq(result.getHospitalSeq()).orElse(0.0))
                .build()).collect(Collectors.toList());
    }

    public List<FreeboardDto> searchInFreeboard(String keyword) {
        QFreeboard freeboard = QFreeboard.freeboard;
        List<Freeboard> results = queryFactory.selectFrom(freeboard)
                .where(freeboard.title.contains(keyword)
                        .or(freeboard.content.contains(keyword)))
                .fetch();

        return results.stream().map(result -> FreeboardDto.builder()
                .freeboardSeq(result.getFreeboardSeq())
                .userId(result.getUser().getUserId())
                .userEmail(hospitalRepository.findById(result.getFreeboardSeq()).get().getEmail())
                .freeboardTitle(result.getTitle())
                .freeboardContent(result.getContent())
                .freeboardRegisterdate(result.getRegisterDate())
                .freeboardCnt(result.getCnt())
                .build()).collect(Collectors.toList());
    }

    public List<ReviewBoardDto> searchInReviewBoard(String keyword) {
        QReviewBoard reviewBoard = QReviewBoard.reviewBoard;
        List<ReviewBoard> results = queryFactory.selectFrom(reviewBoard)
                .where(reviewBoard.title.contains(keyword)
                        .or(reviewBoard.content.contains(keyword))
                        .or(reviewBoard.region.contains(keyword))
                        .or(reviewBoard.doctor.docInfoName.contains(keyword))
                        .or(reviewBoard.surgery.contains(keyword)))
                .fetch();

        return results.stream().map(result -> ReviewBoardDto.builder()
                .reviewBoard_seq(result.getSeq())
                .reviewBoard_title(result.getTitle())
                .reviewBoard_content(result.getContent())
                .reviewBoard_score(result.getScore())
                .customer_name(reviewBoardRepository.findById(result.getSeq()).get().getUser().getName())
                .reviewBoard_doctor(result.getDoctor().getDocInfoName())
                .reviewBoard_region(result.getRegion())
                .reviewBoard_surgery(result.getSurgery())
                .reviewBoard_hospital(result.getHospital().getUser().getName())
                .reviewBoard_cnt(result.getCnt())
                .reviewBoard_score(result.getScore())
                .build()).collect(Collectors.toList());
    }
}
