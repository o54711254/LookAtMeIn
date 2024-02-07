package com.ssafy.lam.chat.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    // chatRoomSeq로 삭제하지 않은 모든 메시지 조회
    List<ChatMessage> findByChatroomChatroomSeqAndDeletedFalse(Long chatRoomSeq);


}