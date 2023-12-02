package com.ssafy.home.user.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.home.user.model.UserDto;
import com.ssafy.home.user.model.mapper.UserMapper;


@Service
public class UserServiceImpl implements UserService {

	
	// DAO와 SERVICE 를 연결 ---------------------------------
	private UserMapper userMapper;
	
	public UserServiceImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	// SERVICE -------------------------------------------
	@Override
	public void signUp(UserDto userDto) throws SQLException {
		userMapper.signUp(userDto);
	}

	@Override
	public UserDto login(UserDto userDto) throws SQLException {
		return userMapper.login(userDto);
	}

	@Override
	public void logout(UserDto userDto) throws SQLException {
		userMapper.logout(userDto);
		
	}
	
	@Override
	public int updateUser(UserDto userDto) throws SQLException {
		// TODO Auto-generated method stub
		return userMapper.updateUser(userDto);
	}
	

	@Override
	public List<UserDto> listUser() throws SQLException {

		return null;
	}

	@Override
	public UserDto selectOne(String id) throws SQLException {

		return userMapper.selectOne(id);
	}

	@Override
	public void saveRefreshToken(String id, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("token", refreshToken);
		userMapper.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String id) throws Exception {

		return userMapper.getRefreshToken(id);
	}

	@Override
	public void deleRefreshToken(String id) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("token", null);
		userMapper.deleteRefreshToken(map);
		
	}


}
