package org.sp.shop.admin.model;

//오직 Product 테이블에 대한 CRUD를 담당할 DAO 객체 정의
public class ProductDAO {
	
	//상품 1건 등록
	public void insert() {
		
		StringBuilder sb=new StringBuilder();
		sb.append("insert into product(prodcut_idx, product_name");
		sb.append(", brand, price, filename, detail");
		sb.append(", subcategory_idx) values(seq_product.nextval, ?, ?, ?, ?, ?, ?)");
		
	}
}
