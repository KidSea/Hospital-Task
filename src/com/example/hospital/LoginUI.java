package com.example.hospital;
/**
 * ��¼����UI
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LoginUI extends JFrame implements ActionListener {

	 //����������
	 private JTextField jtf = null;
	 private JTextArea jta = null;
	 private JButton jb  = null;
	 
	 
	 public LoginUI(){
		 super("���ڽ�������");
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
		jtf = new JTextField("�����ı�����ʾ���յ�������");
		jta = new JTextArea();
		jb = new JButton("�򿪴���");
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("1111");
	}
}