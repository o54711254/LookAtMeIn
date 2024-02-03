package com.ssafy.lam.chat.service;

import com.ssafy.lam.chat.domain.*;
import com.ssafy.lam.chat.dto.ChatMessageDto;
import com.ssafy.lam.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ChatService {

    @Autowired
    private ChatParticipantRepository chatParticipantRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    private Logger log = LoggerFactory.getLogger(ChatService.class);

    public List<Long> getUserChatRoomIds(Long userSeq) {
//        List<ChatParticipant> participants = chatParticipantRepository.findByUserId(userId);
        List<ChatParticipant> participants = chatParticipantRepository.findByUserUserSeq(userSeq);
        Set<Long> uniqueIds = new HashSet<>();
        List<Long> chatRoomSeqs = new ArrayList<>();
        for(ChatParticipant participant : participants) {
            Long chatroomSeq = participant.getChatRoom().getChatroomSeq();
            if(uniqueIds.add(chatroomSeq)){
                chatRoomSeqs.add(chatroomSeq);
            }
        }
        return  chatRoomSeqs;
    }

    // 특정 채팅방의 모든 메시지 조회
    public List<ChatMessage> getMessagesByChatRoomId(Long chatroomSeq) {
        log.info("chatroomSeq : {}", chatroomSeq);
        return chatMessageRepository.findByChatroomChatroomSeq(chatroomSeq);
    }

    public ChatMessage saveMessage(ChatMessageDto messageDto){
        System.out.println("messageDto = " + messageDto);
        User user = User.builder().userSeq(messageDto.getSenderSeq()).build();
        ChatRoom chatroom = ChatRoom.builder().chatroomSeq(messageDto.getChatroomSeq()).build();

        ChatMessage chatMessage = ChatMessage.builder()
                .user(user)
                .chatroom(chatroom)
                .message(messageDto.getMessage())
                .build();

        return chatMessageRepository.save(chatMessage);

    }
}
