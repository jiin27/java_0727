package org.sp.shop.admin.view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.sp.shop.admin.domain.Admin;
import org.sp.shop.admin.model.AdminDAO;

import util.DBManager;

public class LoginForm extends JFrame{
	AdminMain adminMain;
	JTextField t_id;
	JPasswordField t_pass;
	JButton bt;
	
	//DAO 를 이용하여 db관련 업무를 수행하므로 DAO 를 보유하자
	AdminDAO adminDAO;
	Admin adminDTO;
	DBManager dbManager;
	
	public LoginForm(AdminMain adminMain) {
		super("관리자 로그인"); //윈도우 프레임 이름
		this.adminMain = adminMain; //메인 프레임 넘겨 받기
		
		t_id = new JTextField();
		t_pass = new JPasswordField();
		bt = new JButton("Login");
		dbManager = new DBManager();
		adminDAO = new AdminDAO(dbManager);
		
		//스타일
		Dimension d = new Dimension(360, 45);
		t_id.setPreferredSize(d);
		t_pass.setPreferredSize(d);
		
		setLayout(new FlowLayout());
		
		//조립
		add(t_id);
		add(t_pass);
		add(bt);
		
		setSize(400, 220);
		setVisible(true);
		setLocationRelativeTo(null);	
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		//재정의할 메서드가 1개일 경우 클래스까지 만들 필요 없음. 함수 기반의 코드 작성 방법인 Lambda 람다 표현식으로.
		bt.addActionListener((e)->{
			loginCheck();
		});
	}
	
	//로그인에 성공하면 관리자 메인 프레임을 보이게 처리하자
	public void loginCheck() {
		//사용자가 입력한 아이디와 패스워드를 채워넣을 빈(empty) DTO 생성
		String id=t_id.getText();
		String pass= new String(t_pass.getPassword());
		
		Admin admin = new Admin();
		admin.setId(id); //아이디 대입
		admin.setPass(pass); //비번 대입
		
		//login() 메서드 호출 후 반환되는 DTO의 값이 null이 아니라면, 로그인 성공으로 판단
		adminDTO=adminDAO.login(admin); 
		
		if(adminDTO==null) {
			JOptionPane.showMessageDialog(this, "로그인 정보가 올바르지 않습니다.");
		}else {
			JOptionPane.showMessageDialog(this, "관리자 인증 성공");
			//메인 프레임 보여주기
			adminMain.login();

		}
	}
	
}
