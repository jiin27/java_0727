package org.sp.shop.admin.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.sp.shop.admin.domain.Product;

import util.DBManager;

//오직 Product 테이블에 대한 CRUD를 담당할 DAO 객체 정의
public class ProductDAO {
	DBManager dbManager;
	
	
	public ProductDAO(DBManager dbManager) {
		this.dbManager = dbManager;
	}
	
	//상품 1건 등록
	public int insert(Product product) {
		Connection con=null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		con=dbManager.connect();
		
		StringBuilder sb=new StringBuilder();
		sb.append("insert into product(product_idx, product_name");
		sb.append(", brand, price, filename, detail");
		sb.append(", subcategory_idx) values(seq_product.nextval, ?, ?, ?, ?, ?, ?)");
		
		/*자바에서 바인드 변수는 ? 사용
		 * 데이터베이스의 성능 향상을 위한 튜닝 목적으로 사용.
		 * 쿼리문에서 변하는 데이터를 바인드 변수로 입력하면 컴파일 대상이 되지 않음. 지속적인 쿼리문의 끊임없는 컴파일을 막음
		 * */
		
		try {
			pstmt=con.prepareStatement(sb.toString());
			//실행 전에 바인드 변수 값이 먼저 할당돼야 한다.
			pstmt.setString(1, product.getProduct_name());
			pstmt.setString(2, product.getBrand());
			pstmt.setInt(3, product.getPrice());
			pstmt.setString(4, product.getFilename());
			pstmt.setString(5, product.getDetail());
			pstmt.setInt(6, product.getSubcategory_idx());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt);
		}
		return result;
	}
	
	//모든 상품 가져오기(하위 카테고리도 함게 - join)
	public void selectAll() {
		StringBuilder sb=new StringBuilder();
		sb.append("select subname, product_idx, product_name, brand, price, filename");
		sb.append(" from subcategory s, product p");
		sb.append(" where s.subcategory_idx = p.subcategory_idx");
		
	}
}
