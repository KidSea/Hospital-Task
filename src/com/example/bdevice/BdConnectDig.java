package com.example.bdevice;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.example.host.HostModel;

public class BdConnectDig extends JDialog implements ActionListener {
	// 定义组件
	JPanel jp1, jp2;
	JLabel jl;

	JTable jt;
	JScrollPane jsp;

	JLabel jl2;
	JButton jb1, jb2;

	private HostModel hm;
	private String str;
	private String bdstr;

	public BdConnectDig(JFrame owner, String title, boolean modal,
			BdeviceModel bd, int rowNums) {
		// TODO Auto-generated constructor stub
		super(owner, title, modal);// 调用父类方法

		bdstr = (String) bd.getValueAt(rowNums, 0);

		jp1 = new JPanel();
		jl = new JLabel("请选择一个主机");
		jp1.add(jl);

		jp2 = new JPanel();
		jb1 = new JButton("选择");
		jb1.addActionListener(this);
		jl2 = new JLabel("选择的主机：     ");
		jb2 = new JButton("确认");
		jb2.addActionListener(this);
		jp2.add(jl2);
		jp2.add(jb1);
		jp2.add(jb2);

		// 创建一个数据模型对象
		hm = new HostModel();
		String[] paras = { "1" };
		hm.queryHost("select * from hosadd where 1=?", paras);
		// 初始化Jtable
		jt = new JTable(hm);

		// 初始化jsp
		jsp = new JScrollPane(jt);

		this.add(jp1, "North");
		this.add(jsp);
		this.add(jp2, "South");

		this.setSize(500, 350);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == jb1) {
			// 获取选取的行
			int rowNum = this.jt.getSelectedRow();
			if (rowNum == -1) {
				// 提示
				JOptionPane.showMessageDialog(this, "选择一行");
				return;
			}

			str = "";
			str = (String) hm.getValueAt(rowNum, 0);
			jl2.setText("选择的主机:" + str);
		} else if (e.getSource() == jb2) {
			// 做一个SQL
			// 编译语句对象
			String sql = "update bdadd set 匹配对象=? where 序列=?";
			String[] paras = { str, bdstr };
			BdeviceModel bd = new BdeviceModel();
			bd.updBdevice(sql, paras);
			this.dispose();
		}

	}

}
