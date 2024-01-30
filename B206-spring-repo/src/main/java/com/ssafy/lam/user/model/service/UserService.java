package com.ssafy.lam.user.model.service;

import com.ssafy.lam.user.dto.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
//    Customer getCustomer(int seq);
    User getUser(String userId);
    User createUser(User user);
    User updateUser(String userId, User updatedUser);

    void deleteUser(String userId);

//    Customer loginCustomer(String name, String password);

//    public UserDetails loadByName(String name);

//    Customer save(Customer user);

    // 로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인

    User login(User user) throws Exception;
    User findMemberByUserId(String userId) throws Exception;
    void saveRefreshToken(String userId, String refreshToken) throws Exception;
    Object getRefreshToken(String userId) throws Exception;
    void deleteRefreshToken(String userId) throws Exception;

    public int saveMember(User user) throws SQLException;
    public int modifyMember(User user) throws SQLException;
    int deleteMember(String userId) throws SQLException;

}
