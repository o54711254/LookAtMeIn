package com.ssafy.lam.reviewBoard.domain;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ReviewBoardRepositoryImpl implements ReviewBoardRepository {

    private EntityManager em;

    @Autowired
    public ReviewBoardRepositoryImpl(EntityManager em) {
        this.em = em;
    }

//    @Override
//    public double findAvgByHospitalName(String hospitalName) {
//        String query ="select avg(r) from ReviewBoard r join Hospital h where h.user.name = :hospital";
//        int cnt = em.createQuery(query, Integer.class)
////                .setParameter("userId", userId)
//                .getSingleResult();
//        return 0;
//    }

}
