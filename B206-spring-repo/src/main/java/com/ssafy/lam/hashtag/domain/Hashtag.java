package com.ssafy.lam.hashtag.domain;

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
    private long seq;

    private String tagName;

    @OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewHashtag> reviewHashtags;


    @Builder

    public Hashtag(long seq, String tagName) {
        this.seq = seq;
        this.tagName = tagName;
    }
}