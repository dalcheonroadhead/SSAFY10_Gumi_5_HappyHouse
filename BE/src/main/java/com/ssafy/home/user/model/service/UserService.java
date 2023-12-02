package com.ssafy.home.user.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.home.user.model.UserDto;

public interface UserService {
	//회원가입
	public void signUp(UserDto userDto) throws SQLException;
	
	//로그인
	public UserDto login(UserDto userDto) throws SQLException;
	
	//로그아웃
	public void logout(UserDto userDto) throws SQLException;
	
	//회원 정보 수정 
	public int updateUser(UserDto userDto) throws SQLException;
	
	//회원 리스트 조회
	public List<UserDto> listUser() throws SQLException;
	
	//회원 한명 조회 
	public UserDto selectOne(String id) throws SQLException;
	
	
	//회원 RT 저장
	void saveRefreshToken(String id, String refreshToken) throws Exception;
	
	//회원 RT 유효성 검사
	Object getRefreshToken(String id) throws Exception;
	
	//회원 RT 삭제 
	void deleRefreshToken(String id) throws Exception;
	
	

}
