package com.ssafy.lam.hospital.domain;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
