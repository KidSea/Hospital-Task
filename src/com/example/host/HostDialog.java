package com.example.host;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.example.bdevice.BdeviceModel;

public class HostDialog extends JDialog implements ActionListener{
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
	private JFrame Mjframe;
	
	public HostDialog(JFrame owner, String title, boolean modal) {
		// TODO Auto-generated constructor stub
		super(owner, title, modal);
		Mjframe = owner; 
		initData();

		jp = new JPanel();

		// 初始化按钮
		jb1 = new JButton("查看连接");
		jb1.addActionListener(this);
		jb2 = new JButton("删除");
		jb2.addActionListener(this);
		//jb3 = new JButton("发送连接信息");
		
		

		// 把各个按钮加入到jp
		jp.add(jb1);
		jp.add(jb2);
		//jp.add(jb3);

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
		//this.setTitle("主机地址管理");

		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
			System.out.println("用户想查看连接");
		}else if (e.getSource() == jb2) {
			System.out.println("用户想删除");
			//点击这个按钮说明用户想删除
			//得到该从机的ID
			//如果该用户一行都没选择则返回-1
			int rowNum = jt.getSelectedRow();
			if(rowNum == -1){
				// 提示
				JOptionPane.showMessageDialog(this, "选择一行");
				return;
			}
			//删除数据库中的一条记录 
			String bdId = (String) hm.getValueAt(rowNum, 0);
			//创建一个SQL语句
			String sql = "delete from hosadd where 序列=?";
			String [] paras ={bdId};
			HostModel temp = new HostModel();
			temp.updHost(sql, paras);
			
			//构建新的数据模型类，并更新
		    hm = new HostModel();
		    String []paras2 = {"1"};
		    hm.queryHost("select * from hosadd where 1=?", paras2);
		    //更新
		    jt.setModel(hm);
		}
	}
}
