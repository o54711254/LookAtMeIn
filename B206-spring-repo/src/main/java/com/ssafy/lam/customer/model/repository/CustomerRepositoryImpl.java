package com.ssafy.lam.customer.model.repository;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class CustomerRepositoryImpl implements CustomerRepository{

    private EntityManager em;

    @Autowired
    public CustomerRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean existsByUserUserId(String userId) {
        String query ="select count(c) from Customer c join User u where u.userId = :userId";
        int cnt = em.createQuery(query, Integer.class)
                .setParameter("userId", userId)
                .getSingleResult();
        return cnt == 0;
    }
}
