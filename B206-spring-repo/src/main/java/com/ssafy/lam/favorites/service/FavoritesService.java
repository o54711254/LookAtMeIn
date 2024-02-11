package com.ssafy.lam.favorites.service;

import com.ssafy.lam.favorites.domain.Favorites;
import com.ssafy.lam.favorites.dto.FavoritesRequestDto;
import com.ssafy.lam.hospital.domain.Hospital;

import java.util.List;

public interface FavoritesService {

    // 고객 번호를 이용해서 고객이 찜한 병원 목록 조회하기
    List<Hospital> getAllFavorites(Long userSeq);

    // 고객 병원 페이지에서 찜 선택 시  해당 병원 찜
    Favorites addFavorites(FavoritesRequestDto favoritesRequestDto);

    // 고객이 병원 페이지에서 이미 찜한 병원 찜 선택 시 찜 취소하기
    Favorites deleteFavorites(FavoritesRequestDto favoritesRequestDto);

}
