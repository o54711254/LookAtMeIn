package com.ssafy.lam.customer.model.service;

import com.ssafy.lam.customer.dto.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomer(int seq);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(int seq, Customer updatedCustomer);

    void deleteCustomer(int seq);

//    Customer loginCustomer(String name, String password);

//    public UserDetails loadByName(String name);

//    Customer save(Customer customer);

    // 로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인

    Customer login(Customer customer) throws Exception;
    Customer findMemberById(String userId) throws Exception;
    void saveRefreshToken(String userId, String refreshToken) throws Exception;
    Object getRefreshToken(String userId) throws Exception;
    void deleRefreshToken(String userId) throws Exception;

    public int saveMember(Customer customer) throws SQLException;
    public int modifyMember(Customer customer) throws SQLException;
    int deleteMember(String userId) throws SQLException;

}
