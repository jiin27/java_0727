package org.sp.shop.admin.view.product;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.sp.shop.admin.domain.Product;
import org.sp.shop.admin.model.ProductDAO;

import util.DBManager;

//JTable 에 정보를 제공해주는 Product 전용 controller
public class ProductModel extends AbstractTableModel{
	ProductDAO productDAO;
	DBManager dbManager;
	List<Product> list;
	String[] column= {"하위 카테고리", "상품코드", "상품명", "브랜드", "가격", "파일명"};
	
	public ProductModel() {
		dbManager = new DBManager();
		productDAO = new ProductDAO(dbManager);
		
	}
	
	public void getProductList() {
		list=productDAO.selectAll(); //모든 레코드 가져오기
		
	}
	
	public int getRowCount() {
		return list.size();
	}

	public int getColumnCount() {
		return 6;
	}
	
	public String getColumnName(int col) {
		return column[col];
	}

	public Object getValueAt(int row, int col) {
		Product product=list.get(row); //층을 먼저 접근
		
		String value="";
		
		switch (col) {
			case 0: value=product.getSubCategory().getSubname(); break;
			case 1: value=Integer.toString(product.getProduct_idx()); break;
			case 2: value=product.getProduct_name(); break;
			case 3: value=product.getBrand(); break;
			case 4: value=Integer.toString(product.getPrice()); break;
			case 5: value=product.getFilename(); break;
		}
		return value;
	}

}
