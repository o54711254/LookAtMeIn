package com.ssafy.lam.chat.service;

import com.ssafy.lam.chat.domain.*;
import com.ssafy.lam.chat.dto.ChatMessageDto;
import com.ssafy.lam.chat.dto.ChatRoomRequestDto;
import com.ssafy.lam.chat.dto.ChatRoomResponseDto;
import com.ssafy.lam.requestboard.domain.Response;
import com.ssafy.lam.requestboard.domain.ResponseRepository;
import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.user.domain.UserRepository;
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

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResponseRepository responseRepository;

    private Logger log = LoggerFactory.getLogger(ChatService.class);

    public List<Long> getChatRoomIdsByUserSeq(Long userSeq) {
//        List<ChatParticipant> participants = chatParticipantRepository.findByUserId(userId);
        List<ChatParticipant> participants = chatParticipantRepository.findByUserUserSeqAndDeletedFalse(userSeq);
        Set<Long> uniqueIds = new HashSet<>();
        List<Long> chatRoomSeqs = new ArrayList<>();
        for (ChatParticipant participant : participants) {
            Long chatroomSeq = participant.getChatRoom().getChatroomSeq();
            if (uniqueIds.add(chatroomSeq)) {
                chatRoomSeqs.add(chatroomSeq);
            }
        }
        return chatRoomSeqs;
    }

    // 특정 채팅방의 모든 메시지 조회
    public List<ChatMessage> getMessagesByChatRoomId(Long chatroomSeq) {
        log.info("chatroomSeq : {}", chatroomSeq);
        return chatMessageRepository.findByChatroomChatroomSeqAndDeletedFalse(chatroomSeq);
    }

    public ChatMessage saveMessage(ChatMessageDto messageDto) {
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
    public ChatRoomResponseDto createChatRoom(ChatRoomRequestDto chatRoomRequestDto) {

        User hospital = userRepository.findById(chatRoomRequestDto.getHospitalSeq()).get();
        if (!hospital.getUserType().equals("HOSPITAL")) {
            throw new IllegalArgumentException("병원이 아닌 사용자는 채팅방을 생성할 수 없습니다.");
        }

        User customer = userRepository.findById(chatRoomRequestDto.getCustomerSeq()).get();

        // 채팅방 entity 생성
        ChatRoom chatroom = ChatRoom.builder()
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

        ChatRoom chatRoom = chatRoomRepository.save(chatroom);
        chatParticipant1 = chatParticipantRepository.save(chatParticipant1);
        chatParticipant2 = chatParticipantRepository.save(chatParticipant2);

        ChatRoomResponseDto chatRoomResponseDto = ChatRoomResponseDto.builder()
                .chatroomSeq(chatRoom.getChatroomSeq())
                .customerSeq(chatRoomRequestDto.getCustomerSeq())
                .customerId(customer.getUserId())
                .customerName(customer.getName())
                .hospitalSeq(chatRoomRequestDto.getHospitalSeq())
                .hospitalId(hospital.getUserId())
                .hospitalName(hospital.getName())
                .build();

        return chatRoomResponseDto;
    }


    public void closeChatRoom(Long chatRoomSeq, Long userSeq) {

        User user = userRepository.findById(userSeq).get();
        if (!user.getUserType().equals("CUSTOMER")) {
            throw new IllegalArgumentException("사용자가 아닌 병원은 채팅방을 닫을 수 없습니다.");
        }


        // 채팅방 삭제
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomSeq).get();
        chatRoom.setDeleted(true);
        chatRoomRepository.save(chatRoom);


        // 채팅방에서 채팅하던 참가자 삭제
        List<ChatParticipant> chatParticipants = chatParticipantRepository.findByChatRoomChatroomSeqAndDeletedFalse(chatRoomSeq);
        for (ChatParticipant chatParticipant : chatParticipants) {
            chatParticipant.setDeleted(true);
            chatParticipantRepository.save(chatParticipant);
        }


        // 채팅방에 있던 메시지 삭제
        chatMessageRepository.findByChatroomChatroomSeqAndDeletedFalse(chatRoomSeq).forEach(chatMessage -> {
            chatMessage.setDeleted(true);
            chatMessageRepository.save(chatMessage);
        });


    }

    public ChatRoomResponseDto acceptAndCreateChatRoom(Long responseId) {
        Response response = responseRepository.findById(responseId)
                .orElseThrow(() -> new IllegalArgumentException("Response not found with id: " + responseId));

        User customer = response.getRequestboard().getUser();
        User hospital = response.getUser();

        if (!hospital.getUserType().equals("HOSPITAL")) {
            throw new IllegalArgumentException("병원사용자가 아님");
        }

        responseRepository.deleteById(responseId);
        ChatRoomRequestDto chatRoomRequestDto = new ChatRoomRequestDto(hospital.getUserSeq(), customer.getUserSeq());
        return createChatRoom(chatRoomRequestDto);
    }
}
