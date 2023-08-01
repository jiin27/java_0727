package org.sp.shop.admin.view.product;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//상품 등록 화면
public class ListPage extends ProductSubPage{
	JTable table;
	JScrollPane scroll;
	JPanel p_south; //페이징 처리 번호 새겨질 패널
	ProductModel model;
	
		
	public ListPage() {
		model = new ProductModel();
		model.getProductList(); //db연동
		table = new JTable(model);
		scroll = new JScrollPane(table);
		p_south = new JPanel();
		
		setLayout(new BorderLayout());
		
		//조립
		add(scroll); //center에 부착
		add(p_south, BorderLayout.SOUTH);	
	}
	
	//이 패널 페이지를 보이게 하는 자가, 모델이 보유한 상품 조회 메서드를 호출할 수 있도록 메서드를 정의해놓자
	public void getList() {
		model.getProductList(); //db연동
		model.fireTableDataChanged(); //TableModel의 갱신
	}
}
