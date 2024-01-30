package com.ssafy.lam.customer.model.repository;

import com.ssafy.lam.entity.Customer;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public abstract class CustomerRepositoryImpl implements CustomerRepository {

    private final EntityManager em;



//    @Override
//    public String findUserType(Customer customer) {
//        return em.createQuery("select c.user_type from Customer c where c.userId = :userId and c.password = :password", String.class)
//                .setParameter("userId", customer.getUserId())
//                .setParameter("password", customer.getPassword())
//                .getSingleResult();
//    }
}
