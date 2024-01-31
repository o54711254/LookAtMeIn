
package com.ssafy.lam.customer.model.repository;

import com.ssafy.lam.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUserUserSeq(Long userSeq);

    Optional<Customer> findByUserUserId(String userId);

    //
    boolean existsByUserUserId(String userId);


}