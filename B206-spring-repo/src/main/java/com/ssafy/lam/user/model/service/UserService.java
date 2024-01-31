package com.ssafy.lam.user.model.service;

import com.ssafy.lam.entity.User;
import com.ssafy.lam.entity.TokenInfo;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUser(long seq);
    User createUser(User user);
    User updateUser(long seq, User updatedUser);

    void deleteUser(long seq);


    TokenInfo getLoginToken(User user);
//    void logout();
    User findByUserId(String userId) throws Exception;

}
