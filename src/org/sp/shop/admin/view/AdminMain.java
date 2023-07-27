package org.sp.shop.admin.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AdminMain extends JFrame{
	JPanel p_north;
	String[] naviTitle= {"상품 관리", "회원 관리", "주문 관리", "게시판 관리"};
	ArrayList<JLabel> navi;
	JLabel la_login; //로그인 상태 출력 라벨
	JPanel p_center; //각 컨텐츠 페이지들이 들어올 빈 영역
	
	LoginForm loginForm;
	
	//각 페이지의 index가 직관성이 없기 때문에 상수로 표현하자
	public static final int PRODUCT=0;
	public static final int MEMBER=1;
	public static final int ORDER=2;
	public static final int BLOG=3;
	
	//컨텐츠 페이지
	Page[] pages;
	
	public AdminMain() {
		p_north = new JPanel();
		createNavi();
		la_login = new JLabel("");
		p_center = new JPanel();
		pages = new Page[4];
		
		//페이지 생성
		pages[PRODUCT] = new ProductPage();
		pages[MEMBER] = new MemberPage();
		pages[ORDER] = new OrderPage();
		pages[BLOG] = new BlogPage();
		
		//스타일
		p_center.setBackground(Color.YELLOW);
		
		//조립
		p_north.add(la_login);
		for(int i=0; i<pages.length; i++) {
			p_center.add(pages[i]);
		}
		add(p_north, BorderLayout.NORTH);
		add(p_center);
		
		setSize(1100, 600);
		//setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		loginForm = new LoginForm(this);
		
		//최초로 상품페이지는 보여지게
		showHide(PRODUCT);
		
		//네비게이션에 대한 이벤트 연결
		for(int i=0; i<navi.size(); i++) {
			JLabel obj = navi.get(i);
			
			obj.addMouseListener(new MouseAdapter() {
				//마우스 올려놓으면, 배경색을 적용해서 hover 효과내기
				public void mouseEntered(MouseEvent e) {
					JLabel la=(JLabel)e.getSource();
					la.setBackground(Color.BLACK);
					la.setForeground(Color.WHITE);
				}
				
				//마우스를 내려놓으면, 배경색을 빼버리는 효과.
				public void mouseExited(MouseEvent e) {
					JLabel la=(JLabel)e.getSource();
					la.setBackground(null);
					la.setForeground(Color.BLACK);
				}
				
				//클릭시 해당 페이지 보여주기
				public void mouseClicked(MouseEvent e) {
					int index=navi.indexOf(e.getSource()); //이벤트를 일으킨 JLabel이 몇 번째 index에 들어있는지 조사
					
					showHide(index);
				}
			});
		}
		
		//로그아웃 처리
		la_login.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int result=JOptionPane.showConfirmDialog(AdminMain.this, "로그아웃 하시겠어요?");
				if(result==JOptionPane.OK_OPTION) {
					logout();
				}
			}
		});
	}
	
	public void createNavi() {
		navi = new ArrayList<JLabel>();
		for(int i=0; i<naviTitle.length; i++) {
			JLabel obj =new JLabel(naviTitle[i]); // 내비 생성
			navi.add(obj); //리스트에 추가
			p_north.add(obj); //북쪽 패널에 부착
		}
	}
	
	//관리자가 로그인시 호출 될 메서드
	public void login() {
		la_login.setText("로그아웃");
		//프레임 상단 제목에 로그인 한 관리자의 이름 출력
		this.setTitle(loginForm.adminDTO.getName()+" 님 로그인 중");
		
		this.setVisible(true);//현재 메인 프레임을 보이게
		loginForm.setVisible(false); //로그인 폼 안 보이게

	}
	
	//관리자가 로그아웃 시 호출 될 메서드
	public void logout() {
		la_login.setText("로그인");
		this.setTitle("");
		
		//관리자 정보가 들어있던 DTO를 다시 초기화
		loginForm.adminDTO=null;
		
		this.setVisible(false);//현재 메인 프레임을 다시 안보이게
		loginForm.setVisible(true); //로그인 폼 다시 보이게
	}
	
	//페이지 전환처리
	public void showHide(int n) { //보이게 하고 싶은 index 넘겨받는다.
		for(int i=0; i<pages.length; i++) {
			if(i==n) { //넘겨 받은 매개변수와 i가 일치할 때만 보이게
				pages[i].setVisible(true);
			}else{
				pages[i].setVisible(false); //나머지는 안보이게
			}
		}
		
	}
	
	public static void main(String[] args) {
		new AdminMain();
	}
}
