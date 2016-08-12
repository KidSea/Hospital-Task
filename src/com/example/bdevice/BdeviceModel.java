package com.example.bdevice;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.example.sql.SqlHelper;

public class BdeviceModel extends AbstractTableModel {
	// rowData�����洢������
	Vector rowData;
	// columnNames�������
	Vector columnNmaes;

	// ���캯�������г�ʼ��
	public BdeviceModel() {
		// TODO Auto-generated constructor stub
	}

	// ��Ӵӻ���ַ
	public boolean updBdevice(String sql, String[] paras) {
		// ����һ��Sqlhelper
		SqlHelper sqlHelper = new SqlHelper();
		return sqlHelper.updExecute(sql, paras);

	}

	// ��ѯ���ʾ��ǳ�ʼ��
	public void queryBd(String sql, String[] paras) {
		SqlHelper sqlHelper = null;
		// �м�
		columnNmaes = new Vector();
		columnNmaes.add("���");
		columnNmaes.add("��ַ");
		columnNmaes.add("��ַ����");
		columnNmaes.add("ƥ�����");

		// �����ݿ���ȡ������
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
				// ���뵽rowData
				rowData.add(hang);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			sqlHelper.close();
		}

	}

	// �õ���������
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.rowData.size();
	}

	// �õ����ж�����
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.columnNmaes.size();
	}

	// �õ�ĳ��ĳ�е�����
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return ((Vector) this.rowData.get(rowIndex)).get(columnIndex);
	}

	// ��дgetColumnName
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return (String) this.columnNmaes.get(column);
	}
}
