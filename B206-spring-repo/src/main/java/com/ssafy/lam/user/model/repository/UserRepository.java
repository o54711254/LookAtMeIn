package com.ssafy.lam.user.model.repository;

import com.ssafy.lam.user.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    Customer findCustomerByName(String name);
//    Customer login(Customer user) throws SQLException;
//    Customer findMemberById(String userId) throws SQLException;
//    void saveRefreshToken(Map<String, String> map) throws SQLException;
//    Object getRefreshToken(String userid) throws SQLException;
//    void deleteRefreshToken(Map<String, String> map) throws SQLException;
//
//
//
//    int saveMember(Customer user) throws SQLException;
//    public int modifyMember(Customer user) throws SQLException;
//    int deleteMember(String userId) throws SQLException;
    Optional<User> findByUserId(String userId);
    void deleteByUserId(String userId);
}
