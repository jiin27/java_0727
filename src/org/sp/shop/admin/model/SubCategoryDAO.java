package org.sp.shop.admin.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sp.shop.admin.domain.SubCategory;

import util.DBManager;

//오직 SubCategory 테이블에 대한 CRUD 만을 담당하는 객체
public class SubCategoryDAO {
	DBManager dbManager;
	
	public SubCategoryDAO(DBManager dbManager) {
		this.dbManager = dbManager;
	}
	
	//상위 카테고리에 소속된 하위 카테고리 가져오기
	public List selectAllByFkey(int topcategory_idx) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List list = new ArrayList();
				
		try {
			con=dbManager.connect();
			
			String sql="select * from subcategory where topcategory_idx=?";
			pstmt=con.prepareStatement(sql);
			
			pstmt.setInt(1, topcategory_idx);//바인드 변수(?) 값 지정
			rs=pstmt.executeQuery(); //쿼리문 실행
			
			//rs가 곧 close() 되므로, DTO에 담아 List에 채우기
			while(rs.next()) {
				SubCategory sub = new SubCategory();
				sub.setSubcategory_idx(rs.getInt("subcategory_idx"));
				sub.setTopcategory_idx(rs.getInt("topcategory_idx"));
				sub.setSubname(rs.getString("subname"));
				
				list.add(sub);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt, rs);
		}
		return list;
	}
}
