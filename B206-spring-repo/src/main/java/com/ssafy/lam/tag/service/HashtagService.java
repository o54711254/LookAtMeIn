package com.ssafy.lam.tag.service;

import com.ssafy.lam.tag.domain.Hashtag;
import com.ssafy.lam.tag.domain.HashtagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class HashtagService {
    private final HashtagRepository hashtagRepository;

    public Optional<Hashtag> findByTagName(String tagName) {
        return hashtagRepository.findByTagName(tagName);
    }

    public Hashtag save(String tagName) {
        return hashtagRepository.save(
                Hashtag.builder()
                        .tagName(tagName)
                        .build());
    }
}
