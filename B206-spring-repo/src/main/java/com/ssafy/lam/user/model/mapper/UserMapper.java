package com.ssafy.lam.user.model.mapper;

import com.ssafy.lam.user.dto.User;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.Map;

@Mapper
public interface UserMapper {
    User login(User user) throws SQLException;
    User findMemberByUserId(String userId) throws SQLException;
    void saveRefreshToken(Map<String, String> map) throws SQLException;
    Object getRefreshToken(String userid) throws SQLException;
    void deleteRefreshToken(Map<String, String> map) throws SQLException;



    int saveMember(User user) throws SQLException;
    public int modifyMember(User user) throws SQLException;
    int deleteMember(String userId) throws SQLException;

}
