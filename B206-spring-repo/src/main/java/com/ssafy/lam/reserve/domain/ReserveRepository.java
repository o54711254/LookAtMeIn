package com.ssafy.lam.reserve.domain;


import com.ssafy.lam.user.domain.CoordInfo;
import com.ssafy.lam.user.domain.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    // CustomerInfo를 기준으로 예약 목록을 찾는 메소드
    List<Reserve> findAllByCustomerInfo(CustomerInfo customerInfo);

    // CoordInfo를 기준으로 예약 목록을 찾는 메소드
    List<Reserve> findAllByCoordInfo(CoordInfo coordInfo);
}
