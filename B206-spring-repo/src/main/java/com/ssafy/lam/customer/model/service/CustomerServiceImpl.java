package com.ssafy.lam.customer.model.service;

import com.ssafy.lam.customer.model.mapper.CustomerMapper;
import com.ssafy.lam.customer.model.repository.CustomerRepository;
import com.ssafy.lam.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;
//    private final PasswordEncoder passwordEncoder;

    @Autowired
//    public CustomerServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
//        this.customerRepository = customerRepository;
//        this.passwordEncoder = passwordEncoder;
//    }

//    public CustomerServiceImpl(CustomerRepository customerRepository) {
//        this.customerRepository = customerRepository;
//    }
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(int seq) {
        return customerRepository.findById(seq).orElse(null);
    }

    @Override
    public Customer createCustomer(Customer customer) {
//        Customer newCustomer = Customer.builder()
//                .seq(customer.getSeq())
//                .id(customer.getId())
//                .password(customer.getPassword())
//                .build();
//        return customerRepository.save(newCustomer);
        Customer newCustomer = customer.toEntity(customer.getId(), customer.getPassword());
        return customerRepository.save(newCustomer);
    }

    @Override
    public Customer updateCustomer(int seq, Customer updatedCustomer) {
//        if (customerRepository.existsById(seq)) {
//            updatedCustomer.toEntity();
//            return customerRepository.save(updatedCustomer);
//        }
//        return null; // Handle error, customer not found
        Customer oldCustomer = updatedCustomer.toEntity(updatedCustomer.getId(), updatedCustomer.getPassword());
        return customerRepository.save(oldCustomer);
    }

    @Override
    public void deleteCustomer(int seq) {
        customerRepository.deleteById(seq);
    }

//    public UserDetails loadByName(String name) {
//        Customer customer = customerRepository.findCustomerByName(name);
//        if (customer == null) {
//            throw new UsernameNotFoundException("User not found with username: " + name);
//        }
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(customer.getId())
//                .password(customer.getPassword())
//                .roles("USER")
//                .build();
//    }
//
//    @Override
//    public Customer save(Customer customer) {
//        customer.toEntity(customer.getId(), passwordEncoder.encode(customer.getPassword()));
////                .setPassword(passwordEncoder.encode(customer.getPassword()));
//        return customerRepository.save(customer);
//    }

    // 로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인
//    private CustomerMapper customerMapper;
//
//    public CustomerServiceImpl(CustomerMapper customerMapper) {
//        super();
//        this.customerMapper = customerMapper;
//    }

    @Override
    public Customer login(Customer customer) throws Exception {
        return customerMapper.login(customer);
    }

    @Override
    public Customer findMemberById(String userId) throws Exception {
        return customerMapper.findMemberById(userId);
    }

    @Override
    public void saveRefreshToken(String userId, String refreshToken) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        map.put("token", refreshToken);
        customerMapper.saveRefreshToken(map);
    }

    @Override
    public Object getRefreshToken(String userId) throws Exception {
        return customerMapper.getRefreshToken(userId);
    }

    @Override
    public void deleRefreshToken(String userId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        map.put("token", null);
        customerMapper.deleteRefreshToken(map);
    }


    @Override
    public int saveMember(Customer customer) throws SQLException {
        // TODO Auto-generated method stub
        return customerMapper.saveMember(customer);
    }

    @Override
    public int modifyMember(Customer customer) throws SQLException {
        // TODO Auto-generated method stub
        return customerMapper.modifyMember(customer);
    }

    @Override
    public int deleteMember(String userId) throws SQLException {
        // TODO Auto-generated method stub
        return customerMapper.deleteMember(userId);
    }


}
