package com.ssafy.lam.hashtag.service;

import com.ssafy.lam.freeboard.domain.Freeboard;
import com.ssafy.lam.hashtag.domain.FreeboardHashtag;
import com.ssafy.lam.hashtag.domain.FreeboardHashtagRepository;
import com.ssafy.lam.hashtag.domain.Hashtag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FreeboardHashtagService {
    private final HashtagService hashtagService;
    private final FreeboardHashtagRepository freeboardHashtagRepository;

    public void saveHashtag(Freeboard freeboard, List<String> tagNames) {

        if(tagNames.size() == 0) return;

        tagNames.stream()
                .map(hashtag ->
                        hashtagService.findByTagName(hashtag)
                                .orElseGet(() -> hashtagService.save(hashtag)))
                .forEach(hashtag -> mapHashtagToFreeboard(freeboard, hashtag));
    }

    private Long mapHashtagToFreeboard(Freeboard freeboard, Hashtag hashtag) {
        return freeboardHashtagRepository.save(new FreeboardHashtag(freeboard, hashtag)).getSeq();
    }

    public List<FreeboardHashtag> findHashtagListByFreeboard(Freeboard freeboard) {
        return freeboardHashtagRepository.findAllByFreeboard(freeboard);
    }
}
