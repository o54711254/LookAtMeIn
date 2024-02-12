package com.ssafy.lam.favorites.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Long> {

    Optional<List<Favorites>> findAllByUserUserSeqAndIsLikedTrue(Long userSeq);
    // 고객 seq와 병원 seq로 이 둘이 연관되어 있는지 확인(적어도 1번 이상 찜한 경우)
    Favorites findFavoritesByUserUserSeqAndHospitalHospitalSeq(Long userSeq, Long hospitalSeq);
    // 고객 seq와 병원 seq로 이 둘이 연관되어 있고 현재 찜 상태인 경우를 출력
    // 개인이 병원 상세 페이지 조회 시 해당 병원을 찜 했는지 확인용
    // .isIsLiked()로 찜 T/F 확인
    Favorites findFavoritesByUserUserSeqAndHospitalHospitalSeqAndIsLikedTrue(Long userSeq, Long hospitalSeq);

}
