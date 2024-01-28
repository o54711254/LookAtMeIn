package com.ssafy.lam.customer.model.mapper;

import com.ssafy.lam.customer.dto.Customer;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.Map;

@Mapper
public interface CustomerMapper {
    Customer login(Customer customer) throws SQLException;
    Customer findMemberById(String userId) throws SQLException;
    void saveRefreshToken(Map<String, String> map) throws SQLException;
    Object getRefreshToken(String userid) throws SQLException;
    void deleteRefreshToken(Map<String, String> map) throws SQLException;



    int saveMember(Customer customer) throws SQLException;
    public int modifyMember(Customer customer) throws SQLException;
    int deleteMember(String userId) throws SQLException;

}
