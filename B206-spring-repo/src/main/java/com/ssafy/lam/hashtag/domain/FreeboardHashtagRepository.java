package com.ssafy.lam.hashtag.domain;

import com.ssafy.lam.freeboard.domain.Freeboard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeboardHashtagRepository extends JpaRepository<FreeboardHashtag,Long> {
    List<FreeboardHashtag> findAllByFreeboard(Freeboard freeboard);

}
