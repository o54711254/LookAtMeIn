package com.ssafy.lam.consulting;

import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.TokenOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class VideoSessionController {

    private OpenVidu openVidu;

    @Value("${openvidu.url}")
    private String openviduUrl;

    @Value("${openvidu.secret}")
    private String openviduSecret;

    private Map<String, Session> sessionMap = new HashMap<>();

    @PostConstruct
    public void init() {
        this.openVidu = new OpenVidu(openviduUrl, openviduSecret);
    }

    @PostMapping("/api/sessions")
    public Map<String, Object> createSession() {
        try {
            String sessionId;
            Session session;
            if (sessionMap.isEmpty()) { // Create a new session if there isn't any
                session = this.openVidu.createSession();
                sessionId = session.getSessionId();
                sessionMap.put(sessionId, session);
            } else {
                sessionId = sessionMap.keySet().iterator().next();
                session = sessionMap.get(sessionId);
            }

            // Generate token for the session
            String token = session.generateToken(new TokenOptions.Builder().role(OpenViduRole.PUBLISHER).build());

            Map<String, Object> response = new HashMap<>();
            response.put("sessionId", sessionId);
            response.put("token", token);

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create session", e);
        }
    }
}
