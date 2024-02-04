package com.ssafy.lam.chat.controller;


import com.ssafy.lam.chat.domain.ChatMessage;
import com.ssafy.lam.chat.domain.ChatRoom;
import com.ssafy.lam.chat.dto.ChatRoomRequestDto;
import com.ssafy.lam.chat.dto.ChatRoomResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import com.ssafy.lam.chat.dto.ChatMessageDto;
import com.ssafy.lam.chat.service.ChatService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.List;


@RestController

public class WebSocketController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    private Logger log = LoggerFactory.getLogger(WebSocketController.class);


    // 채팅방 생성
    @PostMapping("/chatroom/create")
    @Operation(summary = "채팅방 생성", description = "userSeq를 통해 채팅방을 생성합니다.")
    public ChatRoomResponseDto create(@RequestBody ChatRoomRequestDto chatRoomRequestDto) {
        log.info("chatRoomDto : {}", chatRoomRequestDto);

        ChatRoomResponseDto chatRoomResponseDto = chatService.createChatRoom(chatRoomRequestDto);
        return chatRoomResponseDto;
//        return ResponseEntity.ok(chatRoomResponseDto);
    }


    // 사용자 ID를 통해 속한 채팅방 번호 목록 반환
    @GetMapping("/chatrooms/{userSeq}")
    @Operation(summary = "사용자의 채팅방 목록 조회", description = "userSeq를 통해 사용자가 속한 채팅방 번호 목록을 반환합니다.")
    public List<Long> getUserChatRooms(@PathVariable Long userSeq) {
        log.info("userSeq : {}", userSeq);
        return chatService.getUserChatRoomIds(userSeq);
    }



    // 채팅 메시지 수신 및 저장
    @MessageMapping("/message")
    @Operation(summary = "메시지 전송", description = "메시지를 전송합니다.")
    public ResponseEntity<String> receiveMessage(@RequestBody ChatMessageDto messageDto) {
        // 메시지 저장
//        chatMessageRepository.save(message);
        log.info("messageDto : {}", messageDto);

        ChatMessage chatMessage = chatService.saveMessage(messageDto);

        // 메시지를 해당 채팅방 구독자들에게 전송
        messagingTemplate.convertAndSend("/sub/chatroom/" + messageDto.getChatroomSeq(), messageDto);
        System.out.println(messageDto.toString());

        return ResponseEntity.ok("메시지 전송 완료");
    }

    // 특정 채팅방의 메시지 조회
    @GetMapping("/chatroom/{chatRoomId}/messages")
    @Operation(summary = "특정 채팅방의 메시지 조회", description = "특정 채팅방의 메시지를 조회합니다.")
    public ResponseEntity<List<ChatMessageDto>> getChatRoomMessages(@PathVariable Long chatRoomId) {
        List<ChatMessage> chatMessages= chatService.getMessagesByChatRoomId(chatRoomId);
        List<ChatMessageDto> chatMessageDtos = new ArrayList<>();
        for (ChatMessage chatMessage : chatMessages) {
            System.out.println("chatMessage = " + chatMessage);
            ChatMessageDto chatMessageDto = ChatMessageDto.builder()
                    .chatroomSeq(chatMessage.getChatroom().getChatroomSeq())
                    .sender(chatMessage.getUser().getUserId())
                    .senderSeq(chatMessage.getUser().getUserSeq())
                    .message(chatMessage.getMessage())
                    .messageSeq(chatMessage.getMessageSeq())
                    .build();
            chatMessageDtos.add(chatMessageDto);
        }

//        return chatMessages;
        return ResponseEntity.ok(chatMessageDtos);
    }
}
