package com.example.bdevice;

/**
 * 从机地址管理界面
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

	// 定义控件
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

		// 初始化按钮
		jb1 = new JButton("建立连接");
		jb1.addActionListener(this);
		jb2 = new JButton("删除");
		jb2.addActionListener(this);
		jb3 = new JButton("选择发送端口");
		jb3.addActionListener(this);
		jb4 = new JButton("发送连接信息");
		jb4.addActionListener(this);

		// 把各个按钮加入到jp
		jp.add(jb1);
		jp.add(jb2);
		jp.add(jb3);
		jp.add(jb4);

		// 创建一个数据模型对象
		bd = new BdeviceModel();
		String[] paras = { "1" };
		bd.queryBd("select * from bdadd where 1=?", paras);

		// 初始化Jtable
		jt = new JTable(bd);

		// 初始化jsp
		jsp = new JScrollPane(jt);

		// 讲组件添加
		this.add(jsp);
		this.add(jp, "South");

		this.setSize(500, 500);
		this.setTitle("从机地址管理");

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
			// 用户希望建立连接
			System.out.println("用户想建立连接");
			// 获取选取的行
			int rowNum = this.jt.getSelectedRow();
			if (rowNum == -1) {
				// 提示
				JOptionPane.showMessageDialog(this, "选择一行");
				return;
			}
			// 跳出连接对话框
			new BdConnectDig(this, "连接界面", true, bd, rowNum);

			// 构建新的数据模型类，并更新
			bd = new BdeviceModel();
			String[] paras = { "1" };
			bd.queryBd("select * from bdadd where 1=?", paras);
			// 更新Jtable
			jt.setModel(bd);

		} else if (e.getSource() == jb2) {
			System.out.println("用户想删除");
		} else if (e.getSource() == jb3) {
			// System.out.println("用户想删除");
			SelectDialog dialog = new SelectDialog(this, "选择端口", true);
			port = dialog.port;
			type = 1;
		} else if (e.getSource() == jb4) {

			if (type == 1) {
				// System.out.println("用户想发送连接");
				// 获取选取的行
				int rowNum = this.jt.getSelectedRow();
				if (rowNum == -1) {
					// 提示
					JOptionPane.showMessageDialog(this, "选择一行");
					return;
				}
				String str1 = (String) bd.getValueAt(rowNum, 0);
				String str2 = (String) bd.getValueAt(rowNum, 1);
				String str3 = (String) bd.getValueAt(rowNum, 3);
				if (str3.equals("0")) {
					JOptionPane.showMessageDialog(this, "请连接主机");
					return;
				}

				String str4 = get_hosadd(str3);
				sentdata(str2, str4);

			} else {
				JOptionPane.showMessageDialog(this, "请打开端口");
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
