package com.ssafy.lam.favorites.service;



import com.ssafy.lam.favorites.domain.Favorites;
import com.ssafy.lam.favorites.domain.FavoritesRepository;
import com.ssafy.lam.favorites.dto.FavoritesRequestDto;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.hospital.domain.HospitalRepository;
import com.ssafy.lam.reserve.service.ReserveServiceImpl;
import com.ssafy.lam.user.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoritesServiceImpl implements FavoritesService {

    FavoritesRepository favoritesRepository;
    UserRepository userRepository;
    HospitalRepository hospitalRepository;

    private Logger log = LoggerFactory.getLogger(ReserveServiceImpl.class);

    @Autowired
    public FavoritesServiceImpl(FavoritesRepository favoritesRepository, UserRepository userRepository, HospitalRepository hospitalRepository) {
        this.favoritesRepository = favoritesRepository;
        this.userRepository = userRepository;
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public List<Hospital> getAllFavorites(Long userSeq) {
        List<Favorites> favoritesList = favoritesRepository.findAllByUserSeqAndIsLikedTrue(userSeq).orElse(null);
        List<Hospital> hospitalList = new ArrayList<>();
        if(favoritesList != null) {
            for(Favorites f : favoritesList) {
                Long hospitalSeq = f.getHospitalSeq();
                hospitalList.add(hospitalRepository.findById(hospitalSeq).orElse(null));
            }
        }
        return hospitalList;
    }

    @Override
    public Favorites addFavorites(FavoritesRequestDto favoritesRequestDto) {
        Long userSeq = favoritesRequestDto.getUserSeq();
        Long hospitalSeq = favoritesRequestDto.getHospitalSeq();
        Favorites favorites = favoritesRepository.findFavoritesByUserSeqAndHospitalSeq(userSeq, hospitalSeq);
        if(favorites==null) { // 고객이 찜한 적이 없는 병원인 경우 - 새로 고객과 병원 연결 후 추가
            Favorites newFavorites = Favorites.builder()
                    .userSeq(userSeq)
                    .hospitalSeq(hospitalSeq)
                    .isLiked(true)
                    .build();
            log.info("새 찜 정보: {}", newFavorites);
            favoritesRepository.save(newFavorites);
        }
        else {
            log.info("과거에 찜한 적이 있습니다.");
            // 고객이 과거에 찜한 적이 있는 병원인 경우 - 이미 있는 찜을 불러온 후 isLiked = True로 update
            favorites.setLike(true);
            favoritesRepository.save(favorites);
        }
        return favorites;
    }

    @Override
    public Favorites deleteFavorites(FavoritesRequestDto favoritesRequestDto) {
        Long userSeq = favoritesRequestDto.getUserSeq();
        Long hospitalSeq = favoritesRequestDto.getHospitalSeq();
        Favorites favorites = favoritesRepository.findFavoritesByUserSeqAndHospitalSeqAndIsLikedTrue(userSeq, hospitalSeq);
        if(favorites!=null) {
            favorites.setLike(false);
            favoritesRepository.save(favorites);
        }
        return favorites;
    }

    @Override
    public Boolean isHospitalFavorite(Long userSeq, Long hospitalSeq) {
        Favorites favorites = favoritesRepository.findFavoritesByUserSeqAndHospitalSeqAndIsLikedTrue(userSeq, hospitalSeq);
        if(favorites!=null)
            return true;
        else
            return false;
    }
}