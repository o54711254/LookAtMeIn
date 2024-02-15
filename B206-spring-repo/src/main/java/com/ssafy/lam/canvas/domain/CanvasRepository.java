package com.ssafy.lam.canvas.domain;


import org.springframework.data.jpa.repository.JpaRepository;

public interface CanvasRepository extends JpaRepository<Canvas, Long>{

    Canvas findByCustomerUserSeq(Long userSeq);
}
