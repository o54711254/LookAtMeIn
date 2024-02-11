package com.ssafy.lam.favorites.controller;

import com.ssafy.lam.favorites.domain.Favorites;
import com.ssafy.lam.favorites.dto.FavoritesRequestDto;
import com.ssafy.lam.favorites.service.FavoritesService;
import com.ssafy.lam.freeboard.service.FreeboardServiceImpl;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.dto.HospitalDetailDto;
import com.ssafy.lam.hospital.service.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoritesController {

    private final FavoritesService favoritesService;
    private final HospitalService hospitalService;

    @Autowired
    public FavoritesController(FavoritesService favoritesService, HospitalService hospitalService) {
        this.favoritesService = favoritesService;
        this.hospitalService = hospitalService;
    }

    @GetMapping("/list/{userSeq}")
    @Operation(summary = "해당 고객이 찜한 모든 병원 목록 출력")
    public List<HospitalDetailDto> getFavoritesList(@PathVariable Long userSeq) {
        List<Hospital> hospitalList = favoritesService.getAllFavorites(userSeq);
        List<HospitalDetailDto> hospitalDetailDtoList = new ArrayList<>();
        for(Hospital h : hospitalList) {
            hospitalDetailDtoList.add(hospitalService.getHospitalInfo(h.getUser().getUserSeq()));
        }
        return hospitalDetailDtoList;
    }

    @PostMapping("/add")
    @Operation(summary = "해당 고객이 찜하지 않은 병원을 찜함")
    public ResponseEntity<Favorites> addFavorites(@RequestBody FavoritesRequestDto favoritesRequestDto) {
        Favorites favorites = favoritesService.addFavorites(favoritesRequestDto);
        return new ResponseEntity<>(favorites, HttpStatus.OK);
    }

    @PostMapping("/delete")
    @Operation(summary = "해당 고객이 찜한 병원의 찜을 취소함")
    public ResponseEntity<Favorites> deleteFavorites(@RequestBody FavoritesRequestDto favoritesRequestDto) {
        Favorites favorites = favoritesService.deleteFavorites(favoritesRequestDto);
        return new ResponseEntity<>(favorites, HttpStatus.OK);
    }

}
