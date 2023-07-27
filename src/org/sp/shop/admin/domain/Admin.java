package org.sp.shop.admin.domain;

//이 클래스는 admin 테이블의 1:1 대응되는 DTO 목적으로 정의
//로직이 아닌 오직 데이터를 담기 위한 객체
public class Admin {
	private int admin_idx;
	private String id;
	private String pass;
	private String name;
	
	public int getAdmin_idx() {
		return admin_idx;
	}
	public void setAdmin_idx(int admin_idx) {
		this.admin_idx = admin_idx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
