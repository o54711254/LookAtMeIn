package com.ssafy.lam.user.controller;

import com.ssafy.lam.user.dto.User;
import com.ssafy.lam.user.model.service.UserService;
import com.ssafy.lam.util.JWTUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private final UserService userService;
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, JWTUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // 유저 전체조회 - JPA
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
//        return customerService.getAllCustomers(); - List<Customer>
    }

    // 유저 상세조회 - JPA, userId로 찾기
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserByUserId(@PathVariable String userId) {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    // 유저 추가 - JPA
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
//        return ResponseEntity.ok(createdCustomer);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // 유저 수정 - JPA => seq가 아닌 id로 찾기로 변경해야함
    @Transactional
    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody User updatedUser) {
        User user = userService.updateUser(userId, updatedUser);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
//        return ResponseEntity.ok(user);
    }

    // 유저 삭제 - JPA, userId로 찾기
    @Transactional
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted");
//        return ResponseEntity.noContent().build();
    }

    // 로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인

    // 유저 로그인 - Access Token, Refresh Token 생성, Refresh Token을 DB에 저장 - MyBatis
    @ApiOperation(value = "로그인", notes = "아이디와 비밀번호를 이용하여 로그인 처리.")
    @PostMapping("/regist")
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) User user) {
        log.debug("login user : {}", user);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            User loginUser = userService.login(user);
            if (loginUser != null) {
                String accessToken = jwtUtil.createAccessToken(loginUser.getUserId());
                String refreshToken = jwtUtil.createRefreshToken(loginUser.getUserId());
                log.debug("access token : {}", accessToken);
                log.debug("refresh token : {}", refreshToken);

//				발급받은 refresh token을 DB에 저장.
                userService.saveRefreshToken(loginUser.getUserId(), refreshToken);

//				JSON으로 token 전달.
                resultMap.put("access-token", accessToken);
                resultMap.put("refresh-token", refreshToken);

                status = HttpStatus.CREATED;
            } else {
                resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
                status = HttpStatus.UNAUTHORIZED;
            }

        } catch (Exception e) {
            log.debug("로그인 에러 발생 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    // 유저 회원인증 - MyBatis
    @ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
    @GetMapping("/info/{userId}")
    public ResponseEntity<Map<String, Object>> getInfo(
            @PathVariable("userId") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userId,
            HttpServletRequest request) {
//		logger.debug("userId : {} ", userId);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
            log.info("사용 가능한 토큰!!!");
            try {
//				로그인 사용자 정보.
                User user = userService.findMemberByUserId(userId);
                resultMap.put("userInfo", user);
                status = HttpStatus.OK;
            } catch (Exception e) {
                log.error("정보조회 실패 : {}", e);
                resultMap.put("message", e.getMessage());
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            log.error("사용 불가능 토큰!!!");
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    // 유저 로그아웃 - Refresh Token 삭제 - MyBatis
    @ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
    @GetMapping("/logout/{userId}")
    public ResponseEntity<?> removeToken(
            @PathVariable("userId") @ApiParam(value = "로그아웃할 회원의 아이디.", required = true) String userId) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            userService.deleteRefreshToken(userId);
            status = HttpStatus.OK;
        } catch (Exception e) {
            log.error("로그아웃 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);

    }

    // 유저 Token 재발급 - MyBatis
    @ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody User user, HttpServletRequest request)
            throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        String token = request.getHeader("refreshToken");
        log.debug("token : {}, user : {}", token, user);
        if (jwtUtil.checkToken(token)) {
            if (token.equals(userService.getRefreshToken(user.getUserId()))) {
                String accessToken = jwtUtil.createAccessToken(user.getUserId());
                log.debug("token : {}", accessToken);
                log.debug("정상적으로 액세스토큰 재발급!!!");
                resultMap.put("access-token", accessToken);
                status = HttpStatus.CREATED;
            }
        } else {
            log.debug("리프레쉬토큰도 사용불가!!!!!!!");
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    // 유저 아이디 중복확인 - MyBatis
    @ApiOperation(value = "아이디 중복확인", notes = "아이디가 중복되는지 확인합니다.", response = Map.class)
    @GetMapping("/join/idcheck/{userId}")
    public ResponseEntity<?> idDuplicateCheck(
            @PathVariable("userId") @ApiParam(value = "중복되는지 확인하기 위한 아이디", required = true) String userId) {
        log.debug("login userId : {}", userId);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            User user = userService.findMemberByUserId(userId);
            if (user != null) {
                resultMap.put("userInfo", user);
                status = HttpStatus.OK;
            }

        } catch (Exception e) {
            log.error("정보조회 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);

    }

    // 유저 회원가입 = createCustomer + 조건, 예외처리 추가 - MyBatis
    @ApiOperation(value = "회원확인", notes = "유저를 DB에 저장", response = Map.class)
    @PostMapping("/join")
    public ResponseEntity<?> saveMember(@RequestBody User user) throws Exception {
        log.debug("login user :{}" , user);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            int cnt = userService.saveMember(user);
            if (cnt != 0) {
                status = HttpStatus.CREATED;
            } else {
                resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
                status = HttpStatus.UNAUTHORIZED;
            }

        } catch (Exception e) {
            log.debug("회원가입 에러 발생 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    // 유저 수정 = updateCustomer + 조건, 예외처리 추가 - MyBatis
    @ApiOperation(value = "회원 정보 수정", notes = "DB에 저장되어있는 유저 정보를 수정", response = Map.class)
    @PutMapping("/modify")
    public ResponseEntity<?> modifyMember(@RequestBody User user) throws Exception {
        log.debug("login user :{}" , user);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            int cnt = userService.modifyMember(user);
            if (cnt != 0) {
                status = HttpStatus.CREATED;
            } else {
                resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
                status = HttpStatus.UNAUTHORIZED;
            }

        } catch (Exception e) {
            log.debug("회원정보 수정 에러 발생 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    // 유저 삭제 = deleteCustomer + 조건, 예외처리 추가 - MyBatis
    @ApiOperation(value = "회원 정보 삭제", notes = "DB에 저장되어있는 유저 정보를 삭제", response = Map.class)
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteMember(@PathVariable("userId") String userId ) {
        log.debug("user id :{}", userId);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            int cnt = userService.deleteMember(userId);
            if (cnt != 0)
                status = HttpStatus.OK;
            else {
                resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
                status = HttpStatus.UNAUTHORIZED;
            }
        } catch (Exception e) {
            log.debug("회원 삭제 에러 발생 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

}
