package com.ssafy.lam.customer.model.repository;

import com.ssafy.lam.dto.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
