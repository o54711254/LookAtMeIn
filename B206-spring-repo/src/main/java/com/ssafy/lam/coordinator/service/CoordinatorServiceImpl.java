package com.ssafy.lam.coordinator.service;

import com.ssafy.lam.coordinator.domain.Coordinator;
import com.ssafy.lam.coordinator.domain.CoordinatorRepository;
import com.ssafy.lam.coordinator.dto.CoordinatorDto;
import com.ssafy.lam.hospital.domain.Hospital;
import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CoordinatorServiceImpl {
    private final CoordinatorRepository coordinatorRepository;
    private final UserService userService;

    private Logger log = Logger.getLogger(CoordinatorServiceImpl.class.getName());

//    public Coordinator createCoordinator(CoordinatorDto coordinatorDto){
//        log.info("createCoordinator : {}", coordinatorDto);
//        List<String> roles = new ArrayList<>();
//        roles.add("COORDINATOR");
//
//        User user = User.builder()
//                .name(coordinatorDto.getName())
//                .userId(coordinatorDto.getUserId())
//                .password(coordinatorDto.getPassword())
//                .userType("COORDINATOR")
//                .roles(roles)
//                .build();
//
//        userService.createUser(user);
//
//
////        Coordinator coordinator = Coordinator.builder()
////                .user(user)
////                .hospital()
//
////        return coordinatorRepository.save(coordinator);
//
//    }

}
