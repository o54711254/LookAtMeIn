package com.ssafy.lam.reserve.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Iterator;
import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {

    List<Reserve> findAllById(long id);

}
