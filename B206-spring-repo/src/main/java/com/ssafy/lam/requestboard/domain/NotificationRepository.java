package com.ssafy.lam.requestboard.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findAllByRecipientUserSeq(Long userSeq);
}
