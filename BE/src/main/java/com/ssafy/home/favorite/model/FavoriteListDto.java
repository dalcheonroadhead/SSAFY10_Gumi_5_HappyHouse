package com.ssafy.home.favorite.model;

public class FavoriteListDto {
	private int pk;
	private int like_cnt; 
	private int house_no;
	private String addr_road;
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public int getLike_cnt() {
		return like_cnt;
	}
	public void setLike_cnt(int like_cnt) {
		this.like_cnt = like_cnt;
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
	@Override
	public String toString() {
		return "FavoriteListDto [PK=" + pk + ", like_cnt=" + like_cnt + ", house_no=" + house_no + ", addr_road="
				+ addr_road + "]";
	}
	
	
}
