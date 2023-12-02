package com.ssafy.home.favorite.model;

public class BrokerDto {

	private String PK; 
	private int house_no;
	private String addr_road;
	private String id;
	
	
	public String getPK() {
		return PK;
	}
	public void setPK(String pK) {
		PK = pK;
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
	@Override
	public String toString() {
		return "BrokerDto [PK=" + PK + ", house_no=" + house_no + ", addr_road=" + addr_road + ", id=" + id + "]";
	} 
	
	
	
}
