package com.ssafy.lam.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws")     // 엔드포인트 등록
                .setAllowedOrigins("*");
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        // /sub가 prefix로 붙은 destination의 클라이언트에게 메세지 보낼 수 있도록
        registry.enableSimpleBroker("/api/sub");
        registry.setApplicationDestinationPrefixes("/api/pub");     // /pub가 prefix로 붙은 메세지들은
                                                                // @MessageMapping이 붙은 method로 바운드

    }
}
