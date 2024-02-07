package com.ssafy.lam.tag.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashtagSeq;
    private String tagName;

    @OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewHashtag> reviewHashtags;

    @Builder
    public Hashtag(String tagName) {
        this.tagName = tagName;
    }
}
