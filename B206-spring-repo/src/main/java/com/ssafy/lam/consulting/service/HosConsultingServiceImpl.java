package com.ssafy.lam.consulting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@Slf4j
@RequiredArgsConstructor
public class HosConsultingServiceImpl implements HosConsultingService {
    @Override
    public ResponseEntity<String> initializeSession(Map<String, Object> params) {
        return null;
    }

    @Override
    public ResponseEntity<String> createConnection(String sessionId, Map<String, Object> params) {
        return null;
    }

}
