package com.ssafy.lam.user.service;

import com.ssafy.lam.entity.TokenInfo;
import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.user.domain.UserRepository;
import com.ssafy.lam.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    protected final UserRepository userRepository;

    protected final AuthenticationManagerBuilder authenticationManagerBuilder;

    protected final JwtTokenProvider jwtTokenProvider;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(long seq) {
        return userRepository.findById(seq).orElse(null);
    }

    @Override

    public User createUser(User user) {
        if (userRepository.existsById(user.getUserSeq()))
            throw new RuntimeException("이미 존재하는 고객입니다.");
        log.info("회원가입 User정보: {}", user);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(long seq, User updatedUser) {
//        User oldUser = updatedUser.toEntity(seq, updatedUser.getUserId(), updatedUser.getPassword(), updatedUser.getRoles());
//        User oldUser = updatedUser.toEntity(seq, updatedUser.getUserId(), updatedUser.getName(), updatedUser.getPassword(), updatedUser.getRoles());
        User oldUser = User.builder()
                .userSeq(updatedUser.getUserSeq())
                .userId(updatedUser.getUserId())
                .name(updatedUser.getName())
                .roles(updatedUser.getRoles())
                .password(updatedUser.getPassword())
                .build();
        return userRepository.save(oldUser);
    }

    @Override
    public void deleteUser(long seq) {
        userRepository.deleteById(seq);
    }

    @Override
    @Transactional
    public TokenInfo getLoginToken(User user) {
        TokenInfo tokenInfo = null;
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword());
            System.out.println("authenticationToken = " + authenticationToken);
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            tokenInfo = jwtTokenProvider.generateToken(authentication);
        } catch (AuthenticationException e) {
            System.out.println("AuthenticationException");

            System.out.println("e.getMessage() = " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }

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
