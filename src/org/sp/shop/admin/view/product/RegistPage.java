package org.sp.shop.admin.view.product;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.sp.shop.admin.domain.SubCategory;
import org.sp.shop.admin.domain.TopCategory;
import org.sp.shop.admin.model.SubCategoryDAO;
import org.sp.shop.admin.model.TopCategoryDAO;

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
	
	public RegistPage() {
		box_top = new JComboBox();
		box_sub = new JComboBox();
		t_product_name = new JTextField();
		t_brand = new JTextField();
		t_price = new JTextField();
		
		la_path = new JLabel("이미지를 선택하세요");
		bt_file = new JButton("파일 찾기");
		p_preview = new JPanel(); //추후에 그림을 직접 그릴 것
		area = new JTextArea();
		scroll = new JScrollPane(area);
		p_content = new JPanel();
		
		topCategoryDAO = new TopCategoryDAO();
		subCategoryDAO = new SubCategoryDAO();
		
		//스타일
		p_content.setPreferredSize(new Dimension(700, 550));
		
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
		
		//콤보박스에 상위 카테고리 채우기
		getTopList();
	}
	
	public void getTopList() {
		List<TopCategory> topList = topCategoryDAO.selectAll();
		
		//topList 에 들어있는 요소들(DTO)들 꺼내 콤보박스의 요소로 채우기
		for(int i=0; i<topList.size(); i++) {
			TopCategory topCategory = topList.get(i); //DTO 꺼내기
			box_top.addItem(topCategory.getTopname());			
		}
	}
	
	public void getSubList() {
		List<SubCategory> subList = subCategoryDAO.selectAllByFkey();			
		SubCategory subcategory = subList.
		for(int i=5; i<subList.size(); i++) {
		}
	}
}
