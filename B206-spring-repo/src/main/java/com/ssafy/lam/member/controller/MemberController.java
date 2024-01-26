package com.ssafy.lam.member.controller;

//@RestController
//@RequestMapping("/user")
//@Slf4j
public class MemberController {

//    private Logger log = LoggerFactory.getLogger(MemberController.class);
//
//    private MemberService memberService;
//    private JWTUtil jwtUtil;
//
//    public MemberController(MemberService memberService, JWTUtil jwtUtil) {
//        super();
//        this.memberService = memberService;
//        this.jwtUtil = jwtUtil;
//    }
//
//    @ApiOperation(value = "로그인", notes = "아이디와 비밀번호를 이용하여 로그인 처리.")
//    @PostMapping("/login")
//    public ResponseEntity<Map<String, Object>> login(
//            @RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) MemberDto memberDto) {
//        log.debug("login user : {}", memberDto);
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        HttpStatus status = HttpStatus.ACCEPTED;
//        try {
//            MemberDto loginUser = memberService.login(memberDto);
//            if (loginUser != null) {
//                String accessToken = jwtUtil.createAccessToken(loginUser.getId());
//                String refreshToken = jwtUtil.createRefreshToken(loginUser.getId());
//                log.debug("access token : {}", accessToken);
//                log.debug("refresh token : {}", refreshToken);
//
////				발급받은 refresh token을 DB에 저장.
//                memberService.saveRefreshToken(loginUser.getId(), refreshToken);
//
////				JSON으로 token 전달.
//                resultMap.put("access-token", accessToken);
//                resultMap.put("refresh-token", refreshToken);
//
//                status = HttpStatus.CREATED;
//            } else {
//                resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
//                status = HttpStatus.UNAUTHORIZED;
//            }
//
//        } catch (Exception e) {
//            log.debug("로그인 에러 발생 : {}", e);
//            resultMap.put("message", e.getMessage());
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return new ResponseEntity<Map<String, Object>>(resultMap, status);
//    }
//
//    @ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
//    @GetMapping("/info/{id}")
//    public ResponseEntity<Map<String, Object>> getInfo(
//            @PathVariable("id") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userId,
//            HttpServletRequest request) {
////		logger.debug("userId : {} ", userId);
//        Map<String, Object> resultMap = new HashMap<>();
//        HttpStatus status = HttpStatus.ACCEPTED;
//        if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
//            log.info("사용 가능한 토큰!!!");
//            try {
////				로그인 사용자 정보.
//                MemberDto memberDto = memberService.findMemberById(userId);
//                resultMap.put("userInfo", memberDto);
//                status = HttpStatus.OK;
//            } catch (Exception e) {
//                log.error("정보조회 실패 : {}", e);
//                resultMap.put("message", e.getMessage());
//                status = HttpStatus.INTERNAL_SERVER_ERROR;
//            }
//        } else {
//            log.error("사용 불가능 토큰!!!");
//            status = HttpStatus.UNAUTHORIZED;
//        }
//        return new ResponseEntity<Map<String, Object>>(resultMap, status);
//    }
//
//    @ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
//    @GetMapping("/logout/{id}")
//    public ResponseEntity<?> removeToken(
//            @PathVariable("id") @ApiParam(value = "로그아웃할 회원의 아이디.", required = true) String userId) {
//        Map<String, Object> resultMap = new HashMap<>();
//        HttpStatus status = HttpStatus.ACCEPTED;
//        try {
//            memberService.deleRefreshToken(userId);
//            status = HttpStatus.OK;
//        } catch (Exception e) {
//            log.error("로그아웃 실패 : {}", e);
//            resultMap.put("message", e.getMessage());
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return new ResponseEntity<Map<String, Object>>(resultMap, status);
//
//    }
//
//    @ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
//    @PostMapping("/refresh")
//    public ResponseEntity<?> refreshToken(@RequestBody MemberDto memberDto, HttpServletRequest request)
//            throws Exception {
//        Map<String, Object> resultMap = new HashMap<>();
//        HttpStatus status = HttpStatus.ACCEPTED;
//        String token = request.getHeader("refreshToken");
//        log.debug("token : {}, memberDto : {}", token, memberDto);
//        if (jwtUtil.checkToken(token)) {
//            if (token.equals(memberService.getRefreshToken(memberDto.getId()))) {
//                String accessToken = jwtUtil.createAccessToken(memberDto.getId());
//                log.debug("token : {}", accessToken);
//                log.debug("정상적으로 액세스토큰 재발급!!!");
//                resultMap.put("access-token", accessToken);
//                status = HttpStatus.CREATED;
//            }
//        } else {
//            log.debug("리프레쉬토큰도 사용불가!!!!!!!");
//            status = HttpStatus.UNAUTHORIZED;
//        }
//        return new ResponseEntity<Map<String, Object>>(resultMap, status);
//    }
//
//    @ApiOperation(value = "아이디 중복확인", notes = "아이디가 중복되는지 확인합니다.", response = Map.class)
//    @GetMapping("/join/idcheck/{id}")
//    public ResponseEntity<?> idDuplicateCheck(
//            @PathVariable("id") @ApiParam(value = "중복되는지 확인하기 위한 아이디", required = true) String userId) {
//        log.debug("login userId : {}", userId);
//        Map<String, Object> resultMap = new HashMap<>();
//        HttpStatus status = HttpStatus.ACCEPTED;
//        try {
//            MemberDto memberDto = memberService.findMemberById(userId);
//            if (memberDto != null) {
//                resultMap.put("userInfo", memberDto);
//                status = HttpStatus.OK;
//            }
//
//        } catch (Exception e) {
//            log.error("정보조회 실패 : {}", e);
//            resultMap.put("message", e.getMessage());
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//
//        return new ResponseEntity<Map<String, Object>>(resultMap, status);
//
//    }
//
//    @ApiOperation(value = "회원확인", notes = "유저를 DB에 저장", response = Map.class)
//    @PostMapping("/join")
//    public ResponseEntity<?> saveMember(@RequestBody MemberDto memberDto) throws Exception {
//        log.debug("login user :{}" , memberDto);
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        HttpStatus status = HttpStatus.ACCEPTED;
//        try {
//            int cnt = memberService.saveMember(memberDto);
//            if (cnt != 0) {
//                status = HttpStatus.CREATED;
//            } else {
//                resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
//                status = HttpStatus.UNAUTHORIZED;
//            }
//
//        } catch (Exception e) {
//            log.debug("회원가입 에러 발생 : {}", e);
//            resultMap.put("message", e.getMessage());
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return new ResponseEntity<Map<String, Object>>(resultMap, status);
//    }
//
//    @ApiOperation(value = "회원 정보 수정", notes = "DB에 저장되어있는 유저 정보를 수정", response = Map.class)
//    @PutMapping("/modify")
//    public ResponseEntity<?> modifyMember(@RequestBody MemberDto memberDto) throws Exception {
//        log.debug("login user :{}" , memberDto);
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        HttpStatus status = HttpStatus.ACCEPTED;
//        try {
//            int cnt = memberService.modifyMember(memberDto);
//            if (cnt != 0) {
//                status = HttpStatus.CREATED;
//            } else {
//                resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
//                status = HttpStatus.UNAUTHORIZED;
//            }
//
//        } catch (Exception e) {
//            log.debug("회원정보 수정 에러 발생 : {}", e);
//            resultMap.put("message", e.getMessage());
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return new ResponseEntity<Map<String, Object>>(resultMap, status);
//    }
//
//
//    @ApiOperation(value = "회원 정보 삭제", notes = "DB에 저장되어있는 유저 정보를 삭제", response = Map.class)
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteMember(@PathVariable("id") String userId ){
//        log.debug("user id :{}", userId);
//        Map<String ,Object> resultMap = new HashMap<>();
//        HttpStatus status = HttpStatus.ACCEPTED;
//        try {
//            int cnt = memberService.deleteMember(userId);
//            if(cnt != 0)
//                status = HttpStatus.OK;
//            else {
//                resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
//                status = HttpStatus.UNAUTHORIZED;
//            }
//        }catch(Exception e) {
//            log.debug("회원 삭제 에러 발생 : {}", e);
//            resultMap.put("message", e.getMessage());
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//
//        return new ResponseEntity<Map<String,Object>>(resultMap, status);
//    }

}
