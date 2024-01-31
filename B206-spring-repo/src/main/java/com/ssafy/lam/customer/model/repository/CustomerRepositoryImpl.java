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

//
//    @Override
//    public Optional<Customer> findByUserId(String userId) {
////        String query =
////        Customer findCustomer = em.createQuery(query, Customer.class);
//
//    }
}
