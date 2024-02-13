package com.ssafy.lam.userConsulting.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface ConsultingService {

    ResponseEntity<String> initializeSession(@RequestBody(required = false) Map<String, Object> params);
    ResponseEntity<String> createConnection(@PathVariable("sessionId") String sessionId,
                                            @RequestBody(required = false) Map<String, Object> params);

}
