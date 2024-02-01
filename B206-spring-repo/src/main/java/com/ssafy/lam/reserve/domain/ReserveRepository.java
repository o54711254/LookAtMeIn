package com.ssafy.lam.reserve.domain;


import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.coordinator.domain.Coordinator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    // CustomerInfo를 기준으로 예약 목록을 찾는 메소드
    List<Reserve> findAllByCustomerInfo(Customer customerInfo);

    // CoordInfo를 기준으로 예약 목록을 찾는 메소드
    List<Reserve> findAllByCoordinator(Coordinator coordinator);
}
