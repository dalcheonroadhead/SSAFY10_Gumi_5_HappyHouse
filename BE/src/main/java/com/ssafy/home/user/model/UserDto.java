package com.ssafy.home.user.model;

import io.swagger.annotations.ApiModel;

@ApiModel(value="userDTO : 회원정보", description = "회원의 상세 정보를 나타낸다.")
public class UserDto {
	
	private int pk;
	private String name;
	private String id;
	private String password;
	private String phone;
	private String refreshToken;
	private String userRole;
	private int score;
	
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	} 

	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	
	
	@Override
	public String toString() {
		return "UserDto [pk=" + pk + ", name=" + name + ", id=" + id + ", password=" + password + ", phone=" + phone
				+ ", refreshToken=" + refreshToken + ", userRole=" + userRole + ", score=" + score + "]";
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}


	
}
