package com.ssafy.lam.customer.model.repository;

import com.ssafy.lam.customer.dto.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
//    Customer findCustomerByName(String name);
//    Customer login(Customer customer) throws SQLException;
//    Customer findMemberById(String userId) throws SQLException;
//    void saveRefreshToken(Map<String, String> map) throws SQLException;
//    Object getRefreshToken(String userid) throws SQLException;
//    void deleteRefreshToken(Map<String, String> map) throws SQLException;
//
//
//
//    int saveMember(Customer customer) throws SQLException;
//    public int modifyMember(Customer customer) throws SQLException;
//    int deleteMember(String userId) throws SQLException;
}
