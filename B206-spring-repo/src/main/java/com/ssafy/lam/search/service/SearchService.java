package com.ssafy.lam.search.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.lam.freeboard.domain.Freeboard;
import com.ssafy.lam.freeboard.domain.QFreeboard;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.domain.QHospital;
import com.ssafy.lam.reviewBoard.domain.QReviewBoard;
import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final JPAQueryFactory queryFactory;

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

    private List<Hospital> searchInHospital(String keyword) {
        QHospital hospital = QHospital.hospital;
        return queryFactory.selectFrom(hospital)
                .where(hospital.user.name.contains(keyword)
                        .or(hospital.address.contains(keyword))
                        .or(hospital.email.contains(keyword)))
                .fetch();
    }

    private List<Freeboard> searchInFreeboard(String keyword) {
        QFreeboard freeboard = QFreeboard.freeboard;
        return queryFactory.selectFrom(freeboard)
                .where(freeboard.title.contains(keyword)
                        .or(freeboard.content.contains(keyword)))
                .fetch();
    }


    private List<ReviewBoard> searchInReviewBoard(String keyword) {
        QReviewBoard reviewBoard = QReviewBoard.reviewBoard;
        return queryFactory.selectFrom(reviewBoard)
                .where(reviewBoard.title.contains(keyword)
                        .or(reviewBoard.content.contains(keyword))
                        .or(reviewBoard.region.contains(keyword))
                        .or(reviewBoard.doctor.contains(keyword))
                        .or(reviewBoard.surgery.contains(keyword)))
                .fetch();
    }
}
