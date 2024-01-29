package com.ssafy.lam.customer.controller;

import com.ssafy.lam.customer.model.service.CustomerService;
import com.ssafy.lam.dto.Customer;
import com.ssafy.lam.util.JWTUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("/api/customer")
public class CustomerController {

    private Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private final CustomerService customerService;
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    public CustomerController(CustomerService customerService, JWTUtil jwtUtil) {
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
    }

    // 유저 전체조회 - JPA
    @GetMapping("/all")
    public ResponseEntity<?> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
//        return customerService.getAllCustomers(); - List<Customer>
    }

    // 유저 상세조회 - JPA, id로 찾아오기
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable String id) {
        Customer customer = customerService.getCustomer(id);
        return ResponseEntity.ok(customer);
    }

    // 유저 추가 - JPA
    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
//        return ResponseEntity.ok(createdCustomer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    // 유저 수정 - JPA
    @PutMapping("/update/{seq}")
    public ResponseEntity<?> updateCustomer(@PathVariable int seq, @RequestBody Customer updatedCustomer) {
        Customer customer = customerService.updateCustomer(seq, updatedCustomer);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        }
        return ResponseEntity.notFound().build();
//        return ResponseEntity.ok(customer);
    }

    // 유저 삭제 - JPA
    @DeleteMapping("/delete/{seq}")
    public ResponseEntity<?> deleteCustomer(@PathVariable int seq) {
        customerService.deleteCustomer(seq);
        return ResponseEntity.ok("Customer deleted");
//        return ResponseEntity.noContent().build();
    }

    // 로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인로그인

    // 유저 로그인 - MyBatis
    @ApiOperation(value = "로그인", notes = "아이디와 비밀번호를 이용하여 로그인 처리.")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) Customer customer) {
        log.debug("login user : {}", customer);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            Customer loginUser = customerService.login(customer);
            if (loginUser != null) {
                String accessToken = jwtUtil.createAccessToken(loginUser.getId());
                String refreshToken = jwtUtil.createRefreshToken(loginUser.getId());
                log.debug("access token : {}", accessToken);
                log.debug("refresh token : {}", refreshToken);

//				발급받은 refresh token을 DB에 저장.
                customerService.saveRefreshToken(loginUser.getId(), refreshToken);

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

    // 유저 회원인증 - 아마 권한 확인용? - MyBatis
    @ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
    @GetMapping("/info/{id}")
    public ResponseEntity<Map<String, Object>> getInfo(
            @PathVariable("id") @ApiParam(value = "인증할 회원의 아이디.", required = true) String id,
            HttpServletRequest request) {
//		logger.debug("userId : {} ", userId);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
            log.info("사용 가능한 토큰!!!");
            try {
//				로그인 사용자 정보.
                Customer customer = customerService.findMemberById(id);
                resultMap.put("userInfo", customer);
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

    // 유저 로그아웃 - MyBatis
    @ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
    @GetMapping("/logout/{id}")
    public ResponseEntity<?> removeToken(
            @PathVariable("id") @ApiParam(value = "로그아웃할 회원의 아이디.", required = true) String id) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            customerService.deleRefreshToken(id);
            status = HttpStatus.OK;
        } catch (Exception e) {
            log.error("로그아웃 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);

    }

    // 유저 Refresh Token 재발급 - 딱히 필요 없을 듯? - MyBatis
    @ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Customer customer, HttpServletRequest request)
            throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        String token = request.getHeader("refreshToken");
        log.debug("token : {}, customer : {}", token, customer);
        if (jwtUtil.checkToken(token)) {
            if (token.equals(customerService.getRefreshToken(customer.getId()))) {
                String accessToken = jwtUtil.createAccessToken(customer.getId());
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
    @GetMapping("/join/idcheck/{id}")
    public ResponseEntity<?> idDuplicateCheck(
            @PathVariable("id") @ApiParam(value = "중복되는지 확인하기 위한 아이디", required = true) String id) {
        log.debug("login userId : {}", id);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            Customer customer = customerService.findMemberById(id);
            if (customer != null) {
                resultMap.put("userInfo", customer);
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
    public ResponseEntity<?> saveMember(@RequestBody Customer customer) throws Exception {
        log.debug("login user :{}" , customer);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            int cnt = customerService.saveMember(customer);
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
    public ResponseEntity<?> modifyMember(@RequestBody Customer customer) throws Exception {
        log.debug("login user :{}" , customer);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            int cnt = customerService.modifyMember(customer);
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
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable("id") String id ) {
        log.debug("user id :{}", id);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            int cnt = customerService.deleteMember(id);
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
