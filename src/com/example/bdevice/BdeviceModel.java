package com.example.bdevice;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.example.sql.SqlHelper;

public class BdeviceModel extends AbstractTableModel {
	// rowData变量存储行数据
	Vector rowData;
	// columnNames存放列名
	Vector columnNmaes;

	// 构造函数，进行初始化
	public BdeviceModel() {
		// TODO Auto-generated constructor stub
	}

	// 添加从机地址
	public boolean updBdevice(String sql, String[] paras) {
		// 创建一个Sqlhelper
		SqlHelper sqlHelper = new SqlHelper();
		return sqlHelper.updExecute(sql, paras);

	}

	// 查询本质就是初始化
	public void queryBd(String sql, String[] paras) {
		SqlHelper sqlHelper = null;
		// 中间
		columnNmaes = new Vector();
		columnNmaes.add("序号");
		columnNmaes.add("地址");
		columnNmaes.add("地址类型");
		columnNmaes.add("匹配对象");

		// 从数据库中取出数据
		rowData = new Vector();
		try {
			sqlHelper = new SqlHelper();
			ResultSet rs = sqlHelper.queryExectue(sql, paras);
			while (rs.next()) {
				// rowdata
				Vector hang = new Vector();

				hang.add(rs.getString(1));
				hang.add(rs.getString(2));
				hang.add(rs.getString(3));
				hang.add(rs.getString(4));
				// 加入到rowData
				rowData.add(hang);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			sqlHelper.close();
		}

	}

	// 得到共多少行
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.rowData.size();
	}

	// 得到共有多少列
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.columnNmaes.size();
	}

	// 得到某行某列的数据
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return ((Vector) this.rowData.get(rowIndex)).get(columnIndex);
	}

	// 重写getColumnName
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return (String) this.columnNmaes.get(column);
	}
}
