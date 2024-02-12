package com.ssafy.lam.consulting;

import io.openvidu.java.client.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/video")
public class VideoSessionController {

    private OpenVidu openVidu;
    private Map<String, Session> sessionMap = new HashMap<>();

    @Value("${openvidu.url}")
    private String openviduUrl;

    @Value("${openvidu.secret}")
    private String openviduSecret;

    @PostConstruct
    public void init() {
        this.openVidu = new OpenVidu(openviduUrl, openviduSecret);
    }

    @PostMapping("/sessions")
    public ResponseEntity<?> createSession() {
        try {
            Session session = this.openVidu.createSession();
            String sessionId = session.getSessionId();
            // Store the session with sessionId for later use.
            sessionMap.put(sessionId, session);

            // Generate token with publisher role.
            String token = session.generateToken(new TokenOptions.Builder()
                    .role(OpenViduRole.PUBLISHER)
                    .build());

            Map<String, String> response = new HashMap<>();
            response.put("sessionId", sessionId);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create session");
        }
    }
}
