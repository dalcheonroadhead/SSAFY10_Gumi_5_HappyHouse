package com.ssafy.home.user.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionException;
import com.ssafy.home.user.model.UserDto;


@Mapper
public interface UserMapper {
	
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
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	
	//회원 RT 조회
	Object getRefreshToken(String id) throws SQLException;
	
	//회원 RT 삭제
	void deleteRefreshToken(Map<String, String> map) throws SQLException;
	
}
