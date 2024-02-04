package com.ssafy.lam.chat;

import com.ssafy.lam.chat.controller.WebSocketController;
import com.ssafy.lam.chat.domain.ChatMessage;
import com.ssafy.lam.chat.domain.ChatMessageRepository;
import com.ssafy.lam.chat.domain.ChatRoom;
import com.ssafy.lam.chat.dto.ChatMessageDto;
import com.ssafy.lam.chat.dto.ChatRoomRequestDto;
import com.ssafy.lam.chat.dto.ChatRoomResponseDto;
import com.ssafy.lam.chat.service.ChatService;
import com.ssafy.lam.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class ChatRoomTest {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private WebSocketController webSocketController;

    @Test
    @DisplayName("메시지 저장 테스트")
    @Transactional
    public void saveMessageTest(){
        User user = User.builder().userSeq(1L).build();
        ChatRoom chatroom = ChatRoom.builder().chatroomSeq(1L).build();
        ChatMessageDto chatMessageDto = ChatMessageDto.builder()
                .senderSeq(1L)
                .chatroomSeq(1L)
                .message("안녕하세요")
                .sender("김희수")
                .build();

        ChatMessage chatMessage = chatService.saveMessage(chatMessageDto);
        System.out.println("chatMessage = " + chatMessage);
//
//        ChatMessage chatMessage = ChatMessage.builder()
//                .user(user)
//                .chatroom(chatroom)
//                .message("안녕하세요")
//                .build();
////        ChatMessage savedMessage = chatService.saveMessage(chatMessage);
//        ChatMessage saveMessage = chatMessageRepository.save(chatMessage);
////        Assertions.assertThat(saveMessage.getChatroom().getChatroomSeq()).isEqualTo(chatMessage.getMessageSeq());
//        System.out.println("saveMessage.getChatroom().getChatroomSeq() = " + saveMessage.getChatroom().getChatroomSeq());
//        System.out.println("chatMessage = " + chatMessage.getMessageSeq());
    }

    @Test
    @DisplayName("메시지 조회 테스트")
    @Transactional
    public void getMessagesByChatRoomIdTest(){
        List<ChatMessage> chatMessages = chatMessageRepository.findByChatroomChatroomSeq(1L);
        for(ChatMessage chatMessage : chatMessages){
            System.out.println("chatMessage = " + chatMessage.getMessage());
        }
    }

    @Test
    @DisplayName("채팅방 생성 테스트")
    @Transactional
    public void createChatRoomTest(){
        ChatRoomRequestDto chatRoomRequestDto = ChatRoomRequestDto.builder()
                .customerSeq(1L)
                .hospitalSeq(2L)
                .build();
        ChatRoomResponseDto chatRoomResponseDto = webSocketController.create(chatRoomRequestDto);
        System.out.println("chatRoomResponseDto = " + chatRoomResponseDto);
//        ChatRoomResponseDto chatRoomResponseDto = chatService.createChatRoom(chatRoomRequestDto);
//        System.out.println("chatRoomResponseDto = " + chatRoomResponseDto);
    }
}
