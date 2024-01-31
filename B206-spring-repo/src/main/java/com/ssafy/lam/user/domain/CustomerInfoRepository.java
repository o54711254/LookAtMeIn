package com.ssafy.lam.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Long> {
    // userSeq를 사용하여 CustomerInfo 객체를 찾는 메소드
    List<CustomerInfo> findByUserSeq(Long userSeq);
}
