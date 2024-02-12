package com.ssafy.lam.favorites.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FavoritesRequestDto {

    private Long userSeq;
    private Long hospitalSeq;

    @Builder
    public FavoritesRequestDto(Long userSeq, Long hospitalSeq) {
        this.userSeq = userSeq;
        this.hospitalSeq = hospitalSeq;
    }
}
