package com.ssafy.home.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HttpHeaders;
import com.ssafy.home.user.model.UserDto;
import com.ssafy.home.user.model.service.UserServiceImpl;
import com.ssafy.home.util.JWTUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@RestController
@RequestMapping("/user")
@Api("where is my home API V1")
public class UserController {

	// 컨트롤러와 서비스 연결 
	private UserServiceImpl userServiceImpl;
	private JWTUtil jwtUtil;
	
	// 생성자 주입
	public UserController(UserServiceImpl userServiceImpl, JWTUtil jwtUtil) {
		this.userServiceImpl = userServiceImpl;
		this.jwtUtil = jwtUtil;
	}
	
	@ApiOperation(value="회원가입", notes="회원가입을 한다.")
	@PostMapping(value="/signup", produces="application/json;charset=UTF-8")
	public ResponseEntity<String> signUp(
		@RequestBody @ApiParam(value="회원가입 정보", required = true) UserDto userDto){
		
		System.out.println(userDto);
		
		System.out.println("회원가입 진입");
		try {
			userServiceImpl.signUp(userDto);
			String msg = "회원 가입 성공";
			return new ResponseEntity<>(msg,HttpStatus.CREATED); 
		}catch(Exception e) {
			System.out.println(e.getMessage());
			String msg = "유효하지 않은 요청입니다.";
			return new ResponseEntity<>(msg,HttpStatus.BAD_REQUEST);
		}
		
	}
	@ApiOperation(value="로그인", notes="아이디와 비밀번호를 받아서 로그인 처리")	
	@PostMapping(value="/login", produces="application/json;charset=UTF-8")
	public ResponseEntity<Map<String, Object>> login(
		
		//RequestBody에 담겨져온 내용을 UserDto에 자동 매핑해줌. -> 이름이 같아야함. 
		@RequestBody @ApiParam(value = "로그인 정보", required = true) UserDto userDto,
		HttpServletResponse response
		){
		HttpStatus status = HttpStatus.ACCEPTED;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			System.out.println("넘어온 값: "+userDto);
			
			//작업 - 로그인 하는 내용이 있는지 확인
			UserDto ans = userServiceImpl.login(userDto);
			
			System.out.println(ans);
			
			String msg = "해당 아이디가 있는지 확인 결과: "+ans;
			
			System.out.println(msg);
			System.out.println(ans);
			
			Cookie cookie;

			
			cookie = new Cookie("loginUser", "true");
			if(ans != null) {

				// AT,RT 발급 
				String accessToken = jwtUtil.createAccessToken(ans.getId());
				String refreshToken = jwtUtil.createRefreshToken(ans.getId());
				userServiceImpl.saveRefreshToken(ans.getId(), refreshToken);
				
				// AT와 RT를 resultMap에 담는다.
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token",refreshToken);
				resultMap.put("userInfo", ans);
				resultMap.put("message", "로그인 성공! 수달과 함께 방을 둘러봐요!");
				
				status = HttpStatus.OK;
				
			}else {
				resultMap.put("message", "아이디 또는 패스워드를 확인해주세요 !");
				status = HttpStatus.UNAUTHORIZED;
			}
			
			cookie.setMaxAge(7*24*60*60);
			response.addCookie(cookie);
			
		}catch(Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value="id로 찾기", notes="id 중복확인 및 비밀번호 찾기에 사용")	
	@GetMapping(value="/search/{id}", produces="application/json;charset=UTF-8")
	public ResponseEntity<Map<String, Object>> searchOneById (@PathVariable("id") String id) {
		
		System.out.println(id);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status;
		
		try {
			UserDto ans = userServiceImpl.selectOne(id);
			System.out.println(ans);
			if(ans != null) {
				resultMap.put("userInfo", ans);
				resultMap.put("isDuplicated", ans.getId());
				resultMap.put("password", ans.getPassword());
				status = HttpStatus.OK;
			}else {
				resultMap.put("message", "존재하지 않는 아이디 입니다.");
				status = HttpStatus.BAD_REQUEST;
			}
			
			
			
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			
		}
		
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	@ApiOperation(value="회원정보 수정", notes="회원정보 수정")	
	@PostMapping(value="/update", produces="application/json;charset=UTF-8")
	public ResponseEntity<Map<String, Object>> updateUser(
			@RequestBody @ApiParam(value = "바꿀 id 정보", required = true) UserDto userDto
			) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status;
		
		try {
			int n = userServiceImpl.updateUser(userDto);
			System.out.println(n);
			if(n > 0) {
				UserDto ans = userServiceImpl.selectOne(userDto.getId());
				resultMap.put("userInfo", ans);
				status = HttpStatus.OK;
			}else {
				resultMap.put("message", "회원정보 수정에 실패하였습니다.");
				status = HttpStatus.BAD_REQUEST;
			}
			
			
			
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			
		}
		
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
}
