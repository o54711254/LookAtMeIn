package com.ssafy.lam.chat.service;

import com.ssafy.lam.chat.domain.*;
import com.ssafy.lam.chat.dto.*;
import com.ssafy.lam.common.EncodeFile;
import com.ssafy.lam.config.MultipartConfig;
import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.customer.domain.CustomerRepository;
import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.requestboard.domain.Response;
import com.ssafy.lam.requestboard.domain.ResponseRepository;
import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.user.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Autowired
    private CustomerRepository customerRepository;

    MultipartConfig multipartConfig = new MultipartConfig();
    private String uploadPath = multipartConfig.multipartConfigElement().getLocation();


    private Logger log = LoggerFactory.getLogger(ChatService.class);

    public List<ChatRoomInfoDto> getChatRoomIdsByUserSeq(Long userSeq) {
//        List<ChatParticipant> participants = chatParticipantRepository.findByUserUserSeqAndDeletedFalse(userSeq);
//        Set<Long> uniqueIds = new HashSet<>();
//        List<Long> chatRoomSeqs = new ArrayList<>();
//        for (ChatParticipant participant : participants) {
//            Long chatroomSeq = participant.getChatRoom().getChatroomSeq();
//            if (uniqueIds.add(chatroomSeq)) {
//                chatRoomSeqs.add(chatroomSeq);
//            }
//        }
//        return chatRoomSeqs;
        List<ChatParticipant> participants = chatParticipantRepository.findByUserUserSeqAndDeletedFalse(userSeq);
        List<ChatRoomInfoDto> chatRoomInfos = new ArrayList<>();

        for (ChatParticipant participant : participants) {
            Long chatroomSeq = participant.getChatRoom().getChatroomSeq();
            String chatRoomName = participant.getChatRoomName(); // 사용자별 채팅방 이름 가져오기
            chatRoomInfos.add(new ChatRoomInfoDto(chatroomSeq, chatRoomName));
        }

        return chatRoomInfos;
    }

    // 특정 채팅방의 모든 메시지 조회
    public List<ChatMessageReadDto> getMessagesByChatRoomId(Long chatroomSeq) {
        log.info("chatroomSeq : {}", chatroomSeq);

        List<ChatMessage> chatMessages = chatMessageRepository.findByChatroomChatroomSeqAndDeletedFalse(chatroomSeq);
        List<ChatMessageReadDto> chatMessageReadDtos = new ArrayList<>();

        for (ChatMessage chatMessage : chatMessages) {
            log.info("chatMessage : {}", chatMessage);
            Customer customer = customerRepository.findByUserUserSeq(chatMessage.getUser().getUserSeq())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

            ChatMessageReadDto chatMessageReadDto = ChatMessageReadDto.builder()
                    .chatroomSeq(chatMessage.getChatroom().getChatroomSeq())
                    .sender(chatMessage.getUser().getUserId()) // getUser()가 발신자 User를 반환한다고 가정
                    .senderSeq(chatMessage.getUser().getUserSeq())
                    .message(chatMessage.getMessage())
                    .messageSeq(chatMessage.getMessageSeq())
                    .build();

            if (customer.getProfile() != null) {
                UploadFile customerProfile = customer.getProfile();
                Path path = Paths.get(uploadPath + "/" + customerProfile.getName());
                try {
                    String customerProfileBase64 = EncodeFile.encodeFileToBase64(path);
                    String customerProfileType = customerProfile.getType();
                    chatMessageReadDto.setCustomerProfileBase64(customerProfileBase64);
                    chatMessageReadDto.setCustomerProfileType(customerProfileType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            chatMessageReadDtos.add(chatMessageReadDto);
        }
        return chatMessageReadDtos;
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
        ChatRoom chatroom = ChatRoom.builder().build();
        ChatRoom chatRoom = chatRoomRepository.save(chatroom);

        // 채팅방 참여자인 Hospital entity 생성
        ChatParticipant chatParticipant1 = ChatParticipant.builder()
                .user(hospital)
                .chatRoom(chatroom)
                .chatRoomName(customer.getName())
                .build();

        // 채팅방 참여자인 Customer entity 생성
        ChatParticipant chatParticipant2 = ChatParticipant.builder()
                .user(customer)
                .chatRoom(chatroom)
                .chatRoomName(hospital.getName())
                .build();

        chatParticipantRepository.save(chatParticipant1);
        chatParticipantRepository.save(chatParticipant2);

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
