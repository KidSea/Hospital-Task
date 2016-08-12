package com.example.hospital;
/**
 * 登录界面UI
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LoginUI extends JFrame implements ActionListener {

	 //变量定义区
	 private JTextField jtf = null;
	 private JTextArea jta = null;
	 private JButton jb  = null;
	 
	 
	 public LoginUI(){
		 super("串口接收数据");
		 initView();
		 setSize(500, 500);
		 setVisible(true);
		 jb.addActionListener(this);
		 add(jtf, "South");
		 add(jta, "Center");
		 add(jb, "North");
	 }

	private void initView() {
		// TODO Auto-generated method stub
		jtf = new JTextField("上面文本框显示接收到的数据");
		jta = new JTextArea();
		jb = new JButton("打开串口");
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("1111");
	}
}