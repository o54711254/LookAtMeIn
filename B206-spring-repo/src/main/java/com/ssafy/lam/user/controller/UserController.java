package com.ssafy.lam.user.controller;


import com.ssafy.lam.entity.TokenInfo;
import com.ssafy.lam.user.domain.User;
import com.ssafy.lam.user.dto.UserDto;
import com.ssafy.lam.user.dto.UserTokenInfo;
import com.ssafy.lam.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.antlr.v4.runtime.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")

public class UserController {

    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    @Operation(summary = "모든 고객 정보를 반환한다.")
    public ResponseEntity<List<User>> getAllCustomers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{seq}")
    @Operation(summary = "고객 번호에 해당하는 고객 정보를 반환한다.")
    public ResponseEntity<User> getCustomer(@PathVariable long seq) {
        User user = userService.getUser(seq);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    @PutMapping("/update/{seq}")
//    @Operation(summary = "고객 번호에 해당하는 고객 정보를 수정한다.")
//    public ResponseEntity<User> updateCustomer(@PathVariable long seq, @RequestBody User updatedUser) {
//        User user = userService.updateUser(seq, updatedUser);
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }

    @DeleteMapping("/delete/{seq}")
    @Operation(summary = "고객 번호에 해당하는 고객 정보를 삭제한다.")
    public ResponseEntity<Map<String, Boolean>> deleteCustomer(@PathVariable long seq) {
        log.debug("deleteCustomer seq : {}", seq);
        userService.deleteUser(seq);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    @Operation(summary = "고객 로그인을 수행한다.")
    public ResponseEntity<UserTokenInfo> login(@RequestBody UserDto userDto) throws Exception {
        log.info("login userId : {}", userDto.getUserId());
        User user = userService.findByUserId(userDto.getUserId());
        if (user == null) {
            throw new Exception("아이디가 존재하지 않습니다.");
        }
        if (!user.getPassword().equals(userDto.getUserPassword())) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        TokenInfo tokenInfo = userService.getLoginToken(user);
        UserTokenInfo userTokenInfo = UserTokenInfo.builder()
                .userName(user.getName())
                .userSeq(user.getUserSeq())
                .userType(user.getUserType())
                .userId(user.getUserId())
                .tokenInfo(tokenInfo)
                .build();

        return ResponseEntity.ok(userTokenInfo);




    }


    @GetMapping("/regist/idcheck/{userId}")
    @Operation(summary = "고객 아이디 중복 체크를 수행한다.")
    public ResponseEntity<Boolean> idCheck(@PathVariable String userId) throws Exception {
        log.debug("login userId : {}", userId);
        User user = userService.findByUserId(userId);
        if (user == null) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
    }


    
}
