package com.ssafy.home.favorite.model;

public class HouseDto {
	private int house_no;
	private String addr_road;
	private String addr_dong;
	private String kind; 
	private String house_name;
	private int	household;
	private int floor;
	private int max_floor;
	private String complete_date;
	private String phone; 
	private String content; 
	private int hit; 
	private String register_date;
	
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

	public String getAddr_dong() {
		return addr_dong;
	}
	public void setAddr_dong(String addr_dong) {
		this.addr_dong = addr_dong;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getHouse_name() {
		return house_name;
	}
	public void setHouse_name(String house_name) {
		this.house_name = house_name;
	}
	public int getHousehold() {
		return household;
	}
	public void setHousehold(int household) {
		this.household = household;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public int getMax_floor() {
		return max_floor;
	}
	public void setMax_floor(int max_floor) {
		this.max_floor = max_floor;
	}
	public String getComplete_date() {
		return complete_date;
	}
	public void setComplete_date(String completed_date) {
		this.complete_date = completed_date;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getRegister_date() {
		return register_date;
	}
	public void setRegister_date(String register_date) {
		this.register_date = register_date;
	}
	
	@Override
	public String toString() {
		return "HouseDto [house_no=" + house_no + ", addr_road=" + addr_road + ", addr_dong=" + addr_dong + ", kind="
				+ kind + ", house_name=" + house_name + ", household=" + household + ", floor=" + floor + ", max_floor="
				+ max_floor + ", completed_date=" + complete_date + ", phone=" + phone + ", content=" + content
				+ ", hit=" + hit + ", register_date=" + register_date + "]";
	}
}
