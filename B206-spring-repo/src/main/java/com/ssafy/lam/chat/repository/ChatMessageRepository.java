package com.ssafy.lam.chat.repository;

import com.ssafy.lam.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    // 특정 채팅방의 모든 메시지를 조회
    List<ChatMessage> findByChatRoomId(Long chatRoomId);


}