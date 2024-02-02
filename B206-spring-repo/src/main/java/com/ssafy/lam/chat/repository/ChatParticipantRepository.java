package com.ssafy.lam.chat.repository;

import com.ssafy.lam.chat.entity.ChatParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {
    // 특정 사용자 ID로 모든 참여 정보를 조회
    List<ChatParticipant> findByUserId(String userId);

    // 특정 채팅방 ID로 모든 참여 정보를 조회
    List<ChatParticipant> findByChatRoomId(Long chatRoomId);
}