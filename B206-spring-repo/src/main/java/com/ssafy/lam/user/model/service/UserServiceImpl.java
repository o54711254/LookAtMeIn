package com.ssafy.lam.user.model.service;

import com.ssafy.lam.user.dto.User;
import com.ssafy.lam.user.model.mapper.UserMapper;
import com.ssafy.lam.user.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
//    private final PasswordEncoder passwordEncoder;

    @Autowired
    // Mapper 미사용 시 사용할 코드
//    public CustomerServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
//        this.customerRepository = customerRepository;
//        this.passwordEncoder = passwordEncoder;
//    }

//    public CustomerServiceImpl(CustomerRepository customerRepository) {
//        this.customerRepository = customerRepository;
//    }
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        return userRepository.findByUserId(userId).orElse(null);
    }

    @Override
    public User createUser(User user) {
        User newUser = user.toEntity(user.getSeq(), user.getUserId(), user.getPassword(), user.getToken());
//        Customer newCustomer = user.toEntity();
//        Customer newCustomer = Customer.builder().seq(user.getSeq()).id(user.getId())
//                .password(user.getPassword()).token(user.getToken()).build();
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(String userId, User updatedUser) {
//        User oldUser = updatedUser.toEntity(seq, updatedUser.getUserId(), updatedUser.getPassword(), updatedUser.getToken());
//        Customer oldCustomer = updatedCustomer.toEntity();
//        Customer newCustomer = Customer.builder().seq(seq).id(updatedCustomer.getId())
//                .password(updatedCustomer.getPassword()).token(user.getToken()).build();
//        return userRepository.save(oldUser);
        Optional<User> oldUserOptional = userRepository.findByUserId(userId);
        if(oldUserOptional.isPresent()) {
            User oldUser = oldUserOptional.get();
            oldUser = updatedUser.toEntity(oldUser.getSeq(), userId, updatedUser.getPassword(), updatedUser.getToken());
            return userRepository.save(oldUser);
        }
        return null;
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteByUserId(userId);
    }

    // 로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인
//    private CustomerMapper customerMapper;
//
//    public CustomerServiceImpl(CustomerMapper customerMapper) {
//        super();
//        this.customerMapper = customerMapper;
//    }

    @Override
    public User login(User user) throws Exception {
        return userMapper.login(user);
    }

    @Override
    public User findMemberByUserId(String userId) throws Exception {
        return userMapper.findMemberByUserId(userId);
    }

    @Override
    public void saveRefreshToken(String userId, String refreshToken) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        map.put("token", refreshToken);
        userMapper.saveRefreshToken(map);
    }

    @Override
    public Object getRefreshToken(String userId) throws Exception {
        return userMapper.getRefreshToken(userId);
    }

    @Override
    public void deleteRefreshToken(String userId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        map.put("token", null);
        userMapper.deleteRefreshToken(map);
    }


    @Override
    public int saveMember(User user) throws SQLException {
        return userMapper.saveMember(user);
    }

    @Override
    public int modifyMember(User user) throws SQLException {
        return userMapper.modifyMember(user);
    }

    @Override
    public int deleteMember(String userId) throws SQLException {
        return userMapper.deleteMember(userId);
    }


}
