package com.ssafy.lam.hospital.domain;

import com.ssafy.lam.reviewBoard.domain.ReviewBoard;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public abstract class HospitalRepositoryImpl implements HospitalRepository{

    private final EntityManager em;

    @Autowired
    public HospitalRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Hospital> findByUserUserSeq(Long userSeq) {
        String query= "select h from Hospital h join h.user u where u.userSeq=:userSeq";
        Hospital hospital = em.createQuery(query,Hospital.class)
                .setParameter("userSeq", userSeq)
                .getSingleResult();

        return Optional.of(hospital);

    }

//    @Override
//    public Optional<List<Doctor>> findDoctorByHospitalSeq(Long hospitalSeq) {
//        String query = "select d from Doctor d where d.hospital.hospitalSeq = :hospitalSeq";
//        List<Doctor> doctorList = em.createQuery(query, Doctor.class)
//                .setParameter("hospitalSeq", hospitalSeq)
//                .getResultList();
//        return Optional.of(doctorList);
//    }
}
