package com.ssafy.lam.customer.service;

import com.ssafy.lam.common.EncodeFile;
import com.ssafy.lam.config.MultipartConfig;
import com.ssafy.lam.customer.domain.Customer;
import com.ssafy.lam.customer.domain.CustomerRepository;
import com.ssafy.lam.customer.dto.CustomerDto;
import com.ssafy.lam.entity.TokenInfo;
import com.ssafy.lam.file.domain.UploadFile;
import com.ssafy.lam.file.service.UploadFileService;
import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.user.domain.UserRepository;
import com.ssafy.lam.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UploadFileService uploadFileService;

    private Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    MultipartConfig multipartConfig = new MultipartConfig();
    private String uploadPath = multipartConfig.multipartConfigElement().getLocation();



//    @Override
//    public Customer getCustomer(long seq) {
//        return customerRepository.findById(seq).orElse(null);
//    }

    @Override
    public Customer createCustomer(CustomerDto customerDto) {
        log.info("createCustomer customer :{}", customerDto);
        if (customerRepository.existsByUserUserId(customerDto.getUserId()))
            throw new RuntimeException("이미 존재하는 고객입니다.");

        List<String> roles = new ArrayList<>();
        roles.add("CUSTOMER");

        User user = User.builder()
                .userId(customerDto.getUserId())
                .name(customerDto.getCustomerName())
                .password(customerDto.getUserPassword())
                .roles(roles)
                .userType("CUSTOMER")
                .build();
        userService.createUser(user);
        Customer customer = com.ssafy.lam.customer.domain.Customer.builder()
                .user(user)
                .gender(customerDto.getCustomerGender())
                .tel(customerDto.getCustomerPhoneNumber())
                .email(customerDto.getCustomerEmail())
                .address(customerDto.getCustomerAddress())
                .build();

        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long userSeq, CustomerDto updatedCustomer, MultipartFile profile) {
        User user = userService.getUser(userSeq);
        System.out.println(user.getUserSeq());
        user.setName(updatedCustomer.getCustomerName());
        user.setPassword(updatedCustomer.getUserPassword());
        Customer customer = customerRepository.findByUserUserId(user.getUserId()).orElse(null);
        customer.setUser(user);
        customer.setGender(updatedCustomer.getCustomerGender());
        customer.setTel(updatedCustomer.getCustomerPhoneNumber());
        customer.setEmail(updatedCustomer.getCustomerEmail());
        customer.setAddress(updatedCustomer.getCustomerAddress());

        if(profile != null){
            UploadFile uploadFile = uploadFileService.store(profile);
            customer.setProfile(uploadFile);
        }




        return customerRepository.save(customer);
    }

    @Override
    public Customer findByCustomerId(String customerId) {
        return customerRepository.findByUserUserId(customerId).orElse(null);
    }


    public CustomerDto getCustomer(Long userSeq) {
        Optional<Customer> customerOptional = customerRepository.findByUserUserSeq(userSeq);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            CustomerDto dto = CustomerDto.builder()
                    .userId(customer.getUser().getUserId())
                    .userPassword(customer.getUser().getPassword())
                    .customerName(customer.getUser().getName())
                    .customerGender(customer.getGender())
                    .customerPhoneNumber(customer.getTel())
                    .customerEmail(customer.getEmail())
                    .customerAddress(customer.getAddress())
                    .build();

            if(customer.getProfile() != null){
                Path path = Paths.get(uploadPath+"/"+ customer.getProfile().getName());
                try{
                    String encodeFile = EncodeFile.encodeFileToBase64(path);
                    dto.setType(customer.getProfile().getType());
                    dto.setBase64(encodeFile);
                }catch (IOException e){
                    log.info("파일을 찾을 수 없습니다.");
                    e.printStackTrace();
                }
            }

            return dto;
        } else {
            return null;
        }
    }

    public Customer updateCustomer(long userSeq, CustomerDto customerDto) {
        User user = userService.getUser(userSeq);
        Customer customer = customerRepository.findByUserUserSeq(userSeq).get();

        user.setName(customerDto.getCustomerName());
        user.setPassword(customerDto.getCustomerName());
        customer.setTel(customerDto.getCustomerPhoneNumber());
        customer.setEmail(customerDto.getCustomerEmail());
        customer.setAddress(customerDto.getCustomerAddress());
        userRepository.save(user);

        return customerRepository.save(customer);
    }

}
