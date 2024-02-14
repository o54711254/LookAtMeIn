
package com.ssafy.lam.customer.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUserUserSeq(Long userSeq);
//
    List<Customer> findAllByUserUserSeq(Long userSeq);
    Optional<Customer> findByUserUserId(String userId);

    //
    boolean existsByUserUserId(String userId);


}