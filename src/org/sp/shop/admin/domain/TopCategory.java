package org.sp.shop.admin.domain;

//Topcategory 테이블의 레코드 한 건을 담게 될 DTO
public class TopCategory {
	private int topcategory_idx;
	private String topname;
	
	public int getTopcategory_idx() {
		return topcategory_idx;
	}
	public void setTopcategory_idx(int topcategory_idx) {
		this.topcategory_idx = topcategory_idx;
	}
	public String getTopname() {
		return topname;
	}
	public void setTopname(String topname) {
		this.topname = topname;
	}
	
	
	
}
