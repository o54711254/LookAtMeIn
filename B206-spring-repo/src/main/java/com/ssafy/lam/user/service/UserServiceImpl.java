package com.ssafy.lam.user.service;
import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.user.model.repository.UserRepository;
import com.ssafy.lam.user.domain.TokenInfo;
import com.ssafy.lam.util.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(long seq) {
        return userRepository.findById(seq).orElse(null);
    }

    @Override
    /**
     * 참고 블로그
     * https://suddiyo.tistory.com/entry/Spring-Spring-Security-JWT-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0-4
     * 1편부터 보면 좋다.
     */
    public User createUser(User user) {
        if(userRepository.existsById(user.getSeq()))
            throw new RuntimeException("이미 존재하는 고객입니다.");


        List<String> roles = new ArrayList<>();
        roles.add("CUSTOMER");
        user = user.toEntity(user.getSeq(), user.getId(), user.getPassword(), roles);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(long seq, User updatedUser) {
        User oldUser = updatedUser.toEntity(seq, updatedUser.getId(), updatedUser.getPassword(), updatedUser.getRoles());
        return userRepository.save(oldUser);
    }

    @Override
    public void deleteUser(long seq) {
        userRepository.deleteById(seq);
    }

    @Override
    @Transactional
    public TokenInfo login(User user) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }

    @Override
    public User findByUserId(String userId) throws Exception {
        return userRepository.findByUserId(userId).orElse(null);
    }

//    @Override
//    public void logout() {
//
//    }
}