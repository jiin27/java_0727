package org.sp.shop.admin.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.sp.shop.admin.domain.Admin;

//오직 admin 테이블에 대한 CRUD 만을 담당하기 위한 객체
public class AdminDAO {
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="shop";
	String password="1234";
	
	
	//아래의 로그인 메서드를 호출한 사람에게 결과를 알려줘야 한다.
	//로그인을 성공한 사람은시스템이 기억해야 한다.
	public Admin login(Admin admin) { //Admin (DTO) 클래스에 id, pass 등이 들어있으므로.
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Admin dto=null; //로그인 후 해당 관리자1사람 정보를 담기 위한 객체
		
		try {
			//드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			con=DriverManager.getConnection(url, user, password);
			if(con==null) {
				System.out.println("접속 실패");
			}else {
				//jdbc 프로그래밍시 컬럼의 값은 바인드 변수로 처리가 가능하다
				//이 때 바인드 변수를 jdbc로 표현할 때는 ?를 이용한다
				//바인드 변수란, 데이터베이스 성능을 향상시키기 위한 기법
				String sql="select * from admin where id=? and pass=?";
				pstmt=con.prepareStatement(sql); //쿼리수행 객체 생성
				pstmt.setString(1, admin.getId()); //문장에서 첫 번째로 발견된 물음표.
				pstmt.setString(2, admin.getPass()); //문장에서 두 번째로 발견된 물음표
				
				//쿼리 실행
				rs=pstmt.executeQuery(); //쿼리 수행 후 표를 반환받고, 그 표를 ResultSet 객체로 받음
				if(rs.next()) { //커서를 한 칸 이동시 true가 반환된다면 레코드가 존재한다는 것이므로, 로그인에 성공했다고 판단됨
					dto = new Admin(); //비어있는 dto 인스턴스 생성
					//채워 넣기
					dto.setAdmin_idx(rs.getInt("admin_idx"));
					dto.setId(rs.getString("id"));
					dto.setPass(rs.getString("pass"));
					dto.setName(rs.getString("name"));
				}
			}
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
		return dto;
	}
	
}
