package com.ssafy.lam.chat.service;

import com.ssafy.lam.chat.entity.ChatMessage;
import com.ssafy.lam.chat.entity.ChatParticipant;
import com.ssafy.lam.chat.repository.ChatMessageRepository;
import com.ssafy.lam.chat.repository.ChatParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    private ChatParticipantRepository chatParticipantRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public List<Long> getUserChatRoomIds(String userId) {
        List<ChatParticipant> participants = chatParticipantRepository.findByUserId(userId);
        return participants.stream()
                .map(ChatParticipant::getChatRoomId)
                .distinct()
                .collect(Collectors.toList());
    }

    // 특정 채팅방의 모든 메시지 조회
    public List<ChatMessage> getMessagesByChatRoomId(Long chatRoomId) {
        return chatMessageRepository.findByChatRoomId(chatRoomId);
    }
}
