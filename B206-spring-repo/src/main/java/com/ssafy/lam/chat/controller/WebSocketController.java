package com.ssafy.lam.chat.controller;


import com.ssafy.lam.chat.domain.ChatMessage;
import org.slf4j.Logger;
import com.ssafy.lam.chat.dto.ChatMessageDto;
import com.ssafy.lam.chat.service.ChatService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    // 사용자 ID를 통해 속한 채팅방 번호 목록 반환
    @GetMapping("/chatrooms/{userSeq}")
    public List<Long> getUserChatRooms(@PathVariable Long userSeq) {

        return chatService.getUserChatRoomIds(userSeq);
    }

    // 채팅 메시지 수신 및 저장
    @MessageMapping("/message")
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
