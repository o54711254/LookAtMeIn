package com.ssafy.lam.chat.controller;


import com.ssafy.lam.chat.entity.ChatMessage;
import com.ssafy.lam.chat.repository.ChatMessageRepository;
import com.ssafy.lam.chat.service.ChatService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

@RestController
public class WebSocketController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // 사용자 ID를 통해 속한 채팅방 번호 목록 반환
    @GetMapping("/chatrooms/{userId}")
    @RequestBody
    public List<Long> getUserChatRooms(@PathVariable String userId) {
        return chatService.getUserChatRoomIds(userId);
    }

    // 채팅 메시지 수신 및 저장
    @MessageMapping("/message")
    public void receiveMessage(@RequestBody ChatMessage message) {
        // 메시지 저장
        chatMessageRepository.save(message);

        // 메시지를 해당 채팅방 구독자들에게 전송
        messagingTemplate.convertAndSend("/sub/chatroom/" + message.getChatRoomId(), message);
        System.out.println(message.toString());

    }

    // 특정 채팅방의 메시지 조회
    @GetMapping("/chatroom/{chatRoomId}/messages")
    public List<ChatMessage> getChatRoomMessages(@PathVariable Long chatRoomId) {
        return chatService.getMessagesByChatRoomId(chatRoomId);
    }
}
