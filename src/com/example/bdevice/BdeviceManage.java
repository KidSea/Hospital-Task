package com.example.bdevice;

/**
 * �ӻ���ַ�������
 */
import gnu.io.SerialPort;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.example.host.HostModel;
import com.example.mainui.MainUi;
import com.example.mainui.SelectDialog;
import com.example.sql.SqlHelper;
import com.example.utils.PortUtils;

public class BdeviceManage extends JFrame implements ActionListener {

	// ����ؼ�
	JPanel jp;
	JButton jb1, jb2, jb3, jb4;
	JTable jt;

	public JTable getJt() {
		return jt;
	}

	public void setJt(JTable jt) {
		this.jt = jt;
	}

	JScrollPane jsp;
	BdeviceModel bd;
	private int type;
	String OutputString;
	String port;
	private PortUtils portUtils;
	private SerialPort serialPort;
	private SqlHelper sqlHelper;

	/**
	 * @param args
	 */

	public BdeviceManage() {
		// TODO Auto-generated constructor stub
		initData();

		jp = new JPanel();

		// ��ʼ����ť
		jb1 = new JButton("��������");
		jb1.addActionListener(this);
		jb2 = new JButton("ɾ��");
		jb2.addActionListener(this);
		jb3 = new JButton("ѡ���Ͷ˿�");
		jb3.addActionListener(this);
		jb4 = new JButton("����������Ϣ");
		jb4.addActionListener(this);

		// �Ѹ�����ť���뵽jp
		jp.add(jb1);
		jp.add(jb2);
		jp.add(jb3);
		jp.add(jb4);

		// ����һ������ģ�Ͷ���
		bd = new BdeviceModel();
		String[] paras = { "1" };
		bd.queryBd("select * from bdadd where 1=?", paras);

		// ��ʼ��Jtable
		jt = new JTable(bd);

		// ��ʼ��jsp
		jsp = new JScrollPane(jt);

		// ��������
		this.add(jsp);
		this.add(jp, "South");

		this.setSize(500, 500);
		this.setTitle("�ӻ���ַ����");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == jb1) {
			// �û�ϣ����������
			System.out.println("�û��뽨������");
			// ��ȡѡȡ����
			int rowNum = this.jt.getSelectedRow();
			if (rowNum == -1) {
				// ��ʾ
				JOptionPane.showMessageDialog(this, "ѡ��һ��");
				return;
			}
			// �������ӶԻ���
			new BdConnectDig(this, "���ӽ���", true, bd, rowNum);

			// �����µ�����ģ���࣬������
			bd = new BdeviceModel();
			String[] paras = { "1" };
			bd.queryBd("select * from bdadd where 1=?", paras);
			// ����Jtable
			jt.setModel(bd);

		} else if (e.getSource() == jb2) {
			System.out.println("�û���ɾ��");
		} else if (e.getSource() == jb3) {
			// System.out.println("�û���ɾ��");
			SelectDialog dialog = new SelectDialog(this, "ѡ��˿�", true);
			port = dialog.port;
			type = 1;
		} else if (e.getSource() == jb4) {

			if (type == 1) {
				// System.out.println("�û��뷢������");
				// ��ȡѡȡ����
				int rowNum = this.jt.getSelectedRow();
				if (rowNum == -1) {
					// ��ʾ
					JOptionPane.showMessageDialog(this, "ѡ��һ��");
					return;
				}
				String str1 = (String) bd.getValueAt(rowNum, 0);
				String str2 = (String) bd.getValueAt(rowNum, 1);
				String str3 = (String) bd.getValueAt(rowNum, 3);
				if (str3.equals("0")) {
					JOptionPane.showMessageDialog(this, "����������");
					return;
				}

				String str4 = get_hosadd(str3);
				sentdata(str2, str4);

			} else {
				JOptionPane.showMessageDialog(this, "��򿪶˿�");
				return;
			}

		}
	}

	private String get_hosadd(String str3) {
		// TODO Auto-generated method stub
		String get = "";
		String[] paras = { "1" };
		String sql = "select * from hosadd where 1=?";
		try {
			sqlHelper = new SqlHelper();
			ResultSet rs = sqlHelper.queryExectue(sql, paras);
			while (rs.next()) {

				if (rs.getString(1).equals(str3)) {
					break;
				}
			}
			get = rs.getString(2);
			System.out.println(rs.getString(2));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			sqlHelper.close();
		}
		return get;
	}

	private void sentdata(String str2, String str4) {
		// TODO Auto-generated method stub
		portUtils = new PortUtils();
		portUtils.selectPort(port);
		serialPort = portUtils.getSerialPort();
		OutputString = "2" + str2 + str4;
		portUtils.write(OutputString);
		portUtils.close();
	}

}
