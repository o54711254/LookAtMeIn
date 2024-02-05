package com.ssafy.lam.chat.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {
    // 특정 사용자 seq로 모든 참여 정보를 조회. 삭제되지 않은 것만 조회
//    List<ChatParticipant> findByUserId(String userId);
    List<ChatParticipant> findByUserUserSeqAndDeletedFalse(Long userSeq);


    // 특정 채팅방 ID로 모든 참여 정보를 조회
    List<ChatParticipant> findByChatRoomChatroomSeqAndDeletedFalse(Long chatRoomReq);

}