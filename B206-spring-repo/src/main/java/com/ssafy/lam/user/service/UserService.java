package com.ssafy.lam.user.service;

import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.user.domain.TokenInfo;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUser(long seq);
    User createUser(User user);
    User updateUser(long seq, User updatedUser);

    void deleteUser(long seq);


    TokenInfo login(User user) throws Exception;
//    void logout();
    User findByUserId(String userId) throws Exception;

}
