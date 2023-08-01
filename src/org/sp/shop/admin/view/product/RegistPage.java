package org.sp.shop.admin.view.product;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.sp.shop.admin.domain.Product;
import org.sp.shop.admin.domain.SubCategory;
import org.sp.shop.admin.domain.TopCategory;
import org.sp.shop.admin.model.ProductDAO;
import org.sp.shop.admin.model.SubCategoryDAO;
import org.sp.shop.admin.model.TopCategoryDAO;

import util.DBManager;
import util.StringUtil;

//상품 등록 화면
public class RegistPage extends ProductSubPage{
	JComboBox box_top; //상위 카테고리
	JComboBox box_sub; //하위 카테고리
	
	JTextField t_product_name;
	JTextField t_brand;
	JTextField t_price;
	//대표이미지는 파일 탐색기로 대체
	JLabel la_path; //선택한 이미지 경로 출력
	JButton bt_file; //파일 탐색이 띄우는 버튼
	JPanel p_preview; //선택한 이미지 미리보기
	JTextArea area;
	JScrollPane scroll;
	
	JPanel p_content; //너비 700, 높이 500
	TopCategoryDAO topCategoryDAO;
	SubCategoryDAO subCategoryDAO;
	ProductDAO productDAO;
	List<TopCategory> topList; //콤보 박스에 채워넣을 원본 데이터 (DTO가 들어있음)
	List<SubCategory> subList; 
	
	JFileChooser chooser;
	Image image; //파일 탐색기에서 선택한 파일
	JButton bt_regist;
	
	DBManager dbManager;
	File file; //유저가 선택한 파일
	
	public RegistPage() {
		box_top = new JComboBox();
		box_sub = new JComboBox();
		t_product_name = new JTextField();
		t_brand = new JTextField();
		t_price = new JTextField("0"); //초기값 0으로 세팅
		
		la_path = new JLabel("이미지를 선택하세요");
		bt_file = new JButton("파일 찾기");
		p_preview = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, 130, 130, p_preview); //이미지의 감시자(observer)는 이미지가 담길 패널
			}
		}; //추후에 그림을 직접 그릴 것
		
		area = new JTextArea();
		scroll = new JScrollPane(area);
		p_content = new JPanel();
		dbManager = new DBManager();
		
		topCategoryDAO = new TopCategoryDAO(dbManager);
		subCategoryDAO = new SubCategoryDAO(dbManager);
		productDAO= new ProductDAO(dbManager);
		
		chooser = new JFileChooser("D:/morning/html_workspace/images");
		bt_regist = new JButton("등록");
		
		//스타일
		p_content.setPreferredSize(new Dimension(700, 550));
		p_preview.setPreferredSize(new Dimension(130, 130));
		p_preview.setBackground(Color.DARK_GRAY);
		
		Dimension d = new Dimension(700, 30);
		
		box_top.setPreferredSize(d);
		box_sub.setPreferredSize(d);
		t_product_name.setPreferredSize(d);
		t_brand.setPreferredSize(d);
		t_price.setPreferredSize(d);
		la_path.setPreferredSize(new Dimension(600, 50));
		p_preview.setPreferredSize(new Dimension(130, 100));
		scroll.setPreferredSize(new Dimension(700, 100));
		
		//조립
		p_content.add(box_top);
		p_content.add(box_sub);
		p_content.add(t_product_name);
		p_content.add(t_brand);
		p_content.add(t_price);
		p_content.add(la_path);
		p_content.add(bt_file);
		p_content.add(p_preview);
		p_content.add(scroll);
		
		add(p_content);
		add(bt_regist);
		
		//콤보박스에 상위 카테고리 채우기
		getTopList();
		
		//첫번째 콤보박스에 이벤트 연결
		box_top.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				int index=box_top.getSelectedIndex();
				
				//index를 이용하여 ArrayList의 DTO를 추출하자
				TopCategory dto = topList.get(index);
				int topcategory_idx=dto.getTopcategory_idx(); //pk 추출
				
				System.out.println("pk 값은 " +topcategory_idx);
				getSubList(topcategory_idx);
			}
		});
		
		//가격 텍스트 필드에 이벤트 연결하기 
		t_price.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				//입력한 데이터를 대상으로 parseInt를 시도해 만일 에러가 발생하면, 문자를 넣은 것.
				try {
					Integer.parseInt(t_price.getText());
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(RegistPage.this, "숫자만 입력하세요");
					t_price.setText("0");
				}
			}
		});
		
		//파일 찾기 버튼에 이벤트 연결
		bt_file.addActionListener((e)->{
			openFile();
		});
		
		bt_regist.addActionListener((e)->{
			regist();
		});
	}
	
	//상품 등록하기
	public void regist() {
		//Product DTO에 등록한 내용을 채워 넣기
		Product dto = new Product();
		
		String product_name=t_product_name.getText();
		String brand=t_brand.getText();
		int price=Integer.parseInt(t_price.getText());
		
		//현재 시분초밀리세컨드 로 파일명 만들기
		//확장자는 getExt() 로
		long time=System.currentTimeMillis();
		System.out.println(time);
		String ext=StringUtil.getExt(file.getName());
		
		String filename=time+"."+ext; // 개발자가 파일명을 조작
		String detail=area.getText();
		
		//fkey 구하기 (부모 테이블인 서브카테고리의 pk 가져오기)
		int index=box_sub.getSelectedIndex(); //몇 번째 콤보박스의 아이템을 선택했는지
		SubCategory subCategory=subList.get(index);
		
		//유저가 선택한 이미지를 현재 앱이 인식할 수 있는 경로로 옮겨놓자
		FileInputStream fis=null;
		FileOutputStream fos=null;
		
		 //유저가 선택한 파일을 대상으로 입력 스트림 생성
		try {
			fis=new FileInputStream(file);
			fos=new FileOutputStream("D:/morning/javase_workspace/Shop/product_img/"+filename);
			
			int data=-1;
			while(true) {
				data=fis.read(); //1byte 읽기
				if(data==-1)break;
				fos.write(data);
			}
			//복사완료
			//dto에 데이터 넣기
			dto.setProduct_name(product_name);
			dto.setBrand(brand);
			dto.setPrice(price);
			dto.setFilename(filename);
			dto.setDetail(detail);
			dto.setSubCategory(subCategory); //productDTO 안에 subCategory DTO 넣기
			
			//DAO를 이용하여 오라클에 insert
			int result=productDAO.insert(dto);
			if(result==0) {
				JOptionPane.showMessageDialog(this, "등록되지 않았습니다.");
			}else {
				JOptionPane.showMessageDialog(this, "등록이 완료되었습니다.");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	//파일 탐색기를 띄우고, 그 안에서 원하는 이미지 파일을 선택하면, 해당 이미지를 얻어와 JPanel에 그리기
	public void openFile() {
		int result=chooser.showOpenDialog(this);
		if(result==JFileChooser.APPROVE_OPTION) {
			//p_preview 패널에 그림을 그려넣자
			file=chooser.getSelectedFile();
			
			//file 객체를 이미지로 변환하기
			try {
				image=ImageIO.read(file); //BufferedImage 반환. buffimage는 이미지 변환 가능. 훨씬 다양한 변환이 가능하다.
				//p_preview 패널의 그림 다시 그리기 요청
				//repaint()  -> update() -> paint()
				p_preview.repaint();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void getTopList() {
		topList = topCategoryDAO.selectAll();
		
		//topList 에 들어있는 요소들(DTO)들 꺼내 콤보박스의 요소로 채우기
		for(int i=0; i<topList.size(); i++) {
			TopCategory topCategory = topList.get(i); //DTO 꺼내기
			box_top.addItem(topCategory.getTopname());			
		}
	}
	
	public void getSubList(int topcategory_idx) {
		subList = subCategoryDAO.selectAllByFkey(topcategory_idx);
		
		//기존에 등록된 아이템들이 존재한다면 싹 비운 뒤
		box_sub.removeAllItems();
		
		//하위 카테로기를 두 번째 콤보 박스에 채워넣기
		for(int i=0; i<subList.size(); i++) {
			SubCategory dto=subList.get(i); //DTO 꺼내기
			box_sub.addItem(dto.getSubname());
		}
	}
}
