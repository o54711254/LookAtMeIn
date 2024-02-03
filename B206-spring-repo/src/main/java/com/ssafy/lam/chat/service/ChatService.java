package com.ssafy.lam.chat.service;

import com.ssafy.lam.chat.domain.*;
import com.ssafy.lam.chat.dto.ChatMessageDto;
import com.ssafy.lam.chat.dto.ChatRoomDto;
import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.user.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatService {

    @Autowired
    private ChatParticipantRepository chatParticipantRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

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

    // 채팅방 생성
    public ChatRoom createChatRoom(ChatRoomDto chatRoomDto) {

        User hospital = userRepository.findById(chatRoomDto.getHospitalSeq()).get();
        if(!hospital.getUserType().equals("HOSPITAL")){
            throw new IllegalArgumentException("병원이 아닌 사용자는 채팅방을 생성할 수 없습니다.");
        }

        User customer = userRepository.findById(chatRoomDto.getCustomerSeq()).get();

        // 채팅방 entity 생성
        ChatRoom chatroom = ChatRoom.builder()
                .customerName(customer.getName())
                .hospitalName(hospital.getName())
                .build();


        // 채팅방 참여자인 Hospital entity 생성
        ChatParticipant chatParticipant1 = ChatParticipant.builder()
                .user(hospital)
                .chatRoom(chatroom)
                .build();

        // 채팅방 참여자인 Customer entity 생성
        ChatParticipant chatParticipant2 = ChatParticipant.builder()
                .user(customer)
                .chatRoom(chatroom)
                .build();

        chatRoomRepository.save(chatroom);
        chatParticipantRepository.save(chatParticipant1);
        chatParticipantRepository.save(chatParticipant2);

        return chatroom;

    }
}
