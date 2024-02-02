package com.ssafy.lam.chat;

import com.ssafy.lam.chat.domain.ChatMessage;
import com.ssafy.lam.chat.domain.ChatMessageRepository;
import com.ssafy.lam.chat.domain.ChatRoom;
import com.ssafy.lam.chat.dto.ChatMessageDto;
import com.ssafy.lam.chat.service.ChatService;
import com.ssafy.lam.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ChatMessageTest {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Test
    @DisplayName("메시지 저장 테스트")
    @Transactional
    public void saveMessageTest(){
        User user = User.builder().userSeq(1L).build();
        ChatRoom chatroom = ChatRoom.builder().chatroomSeq(1L).build();
        ChatMessageDto chatMessageDto = ChatMessageDto.builder()
                .userSeq(1L)
                .chatroomSeq(1L)
                .message("안녕하세요")
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
}
