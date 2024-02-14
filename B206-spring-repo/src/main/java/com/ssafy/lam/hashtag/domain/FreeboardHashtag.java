package com.ssafy.lam.hashtag.domain;

import com.ssafy.lam.freeboard.domain.Freeboard;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class FreeboardHashtag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long seq;

    @ManyToOne
    private Freeboard freeboard;

    @ManyToOne
    private Hashtag hashtag;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public FreeboardHashtag(Freeboard freeboard, Hashtag hashtag) {
        this.freeboard = freeboard;
        this.hashtag = hashtag;
    }
}
