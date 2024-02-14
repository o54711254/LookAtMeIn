package com.ssafy.lam.hashtag.service;

import com.ssafy.lam.hashtag.domain.Hashtag;
import com.ssafy.lam.hashtag.domain.HashtagRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

//단순 읽기 저장 메서드
@RequiredArgsConstructor
@Service
public class HashtagService {
    private final HashtagRepository hashtagRepository;

    public Optional<Hashtag> findByTagName(String tagName) {
        return hashtagRepository.findByTagName(tagName);
    }

    public Hashtag save(String tagName) {
        return hashtagRepository.save(Hashtag.builder()
                .tagName(tagName)
                .build());
    }
}
