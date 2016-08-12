package com.example.host;

/**
 * 主机地址管理界面
 */
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class HostManage extends JFrame {

	// 定义控件
	JPanel jp;
	JButton jb1, jb2, jb3;
	JTable jt;

	public JTable getJt() {
		return jt;
	}

	public void setJt(JTable jt) {
		this.jt = jt;
	}

	JScrollPane jsp;
	private HostModel hm;

	/**
	 * @param args
	 */

	// 构造函数，进行界面初始化
	public HostManage() {
		// TODO Auto-generated constructor stub
		initData();

		jp = new JPanel();

		// 初始化按钮
		jb1 = new JButton("查看连接");
		jb2 = new JButton("删除");
		jb3 = new JButton("发送连接信息");

		// 把各个按钮加入到jp
		jp.add(jb1);
		jp.add(jb2);
		jp.add(jb3);

		// 创建一个数据模型对象
		hm = new HostModel();
		String[] paras = { "1" };
		hm.queryHost("select * from hosadd where 1=?", paras);

		// 初始化Jtable
		jt = new JTable(hm);

		// 初始化jsp
		jsp = new JScrollPane(jt);

		// 讲组件添加
		this.add(jsp);
		this.add(jp, "South");

		this.setSize(500, 500);
		this.setTitle("主机地址管理");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);

	}

	private void initData() {
		// TODO Auto-generated method stub

	}
}
