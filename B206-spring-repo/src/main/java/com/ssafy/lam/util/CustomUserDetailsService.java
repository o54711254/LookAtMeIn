package com.ssafy.lam.customer.model.service;

import com.ssafy.lam.customer.model.repository.CustomerRepository;
import com.ssafy.lam.dto.Customer;
import com.ssafy.lam.member.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerDetailsService implements UserDetailsService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return customerRepository.findByUserId(userId)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(Customer customer) {
        return User.builder()
                .username(customer.getUserId())
                .password(passwordEncoder.encode(customer.getPassword()))
                .roles(customer.getRoles().stream().toArray(String[]::new))
                .build();
    }
}
