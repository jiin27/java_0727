package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//어플리케이션이 가동될 때, db에 접속하고, 끝날 때 접속을 해제하기 위해 
//데이터베이스 접속 및 끝내는 처리를 담당할 객체를 별도로 정의해서 공통 코드와 시키자
public class DBManager {
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="shop";
	String password="1234";

	//커넥션 만들기(접속)
	public Connection connect() { //이 메서드를 호출하는 각 DAO가 con을 보유하도록 connection을 반환
		Connection con=null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection(url, user, password);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	//데이터베이스와 관련된 자원 해제
		
	//con, pstmt 만 닫는 경우(DML수행시)
	public void release(Connection con, PreparedStatement pstmt) {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//con, pstmt, rs 를 모두 닫는 경우(select문 수행시)
	public void release(Connection con, PreparedStatement pstmt, ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close(); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	
}
