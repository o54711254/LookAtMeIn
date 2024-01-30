package com.ssafy.lam.user.model.service;

import com.ssafy.lam.dto.User;
import com.ssafy.lam.dto.TokenInfo;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUser(long seq);
    User createUser(User user);
    User updateUser(long seq, User updatedUser);

    void deleteUser(long seq);


    TokenInfo getLoginToken(User user) throws Exception;
//    void logout();
    User findByUserId(String userId) throws Exception;

}
