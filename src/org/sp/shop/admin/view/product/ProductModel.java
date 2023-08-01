package org.sp.shop.admin.view.product;

import javax.swing.table.AbstractTableModel;

//JTable 에 정보를 제공해주는 Product 전용 controller
public class ProductModel extends AbstractTableModel{

	public int getRowCount() {
		return 5;
	}

	public int getColumnCount() {
		return 3;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return "jeans";
	}

}
