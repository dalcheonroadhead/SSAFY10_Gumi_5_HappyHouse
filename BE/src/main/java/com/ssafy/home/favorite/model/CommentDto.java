package com.ssafy.home.favorite.model;

public class CommentDto {
	private int comment_no;
	private int house_no;
	private String addr_road;
	private String id;
	private String password; 
	private String content;
	private int like_cnt;
	private int hate_cnt;
	
	public int getComment_no() {
		return comment_no;
	}
	public void setComment_no(int comment_no) {
		this.comment_no = comment_no;
	}
	public int getHouse_no() {
		return house_no;
	}
	public void setHouse_no(int house_no) {
		this.house_no = house_no;
	}
	public String getAddr_road() {
		return addr_road;
	}
	public void setAddr_road(String addr_road) {
		this.addr_road = addr_road;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getLike_cnt() {
		return like_cnt;
	}
	public void setLike_cnt(int like_cnt) {
		this.like_cnt = like_cnt;
	}
	public int getHate_cnt() {
		return hate_cnt;
	}
	public void setHate_cnt(int hate_cnt) {
		this.hate_cnt = hate_cnt;
	}
}
