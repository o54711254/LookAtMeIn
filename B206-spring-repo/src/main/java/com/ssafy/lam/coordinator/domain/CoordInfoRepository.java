package com.ssafy.lam.coordinator.domain;

import com.ssafy.lam.coordinator.domain.Coordinator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordInfoRepository extends JpaRepository<Coordinator,Long> {
}
