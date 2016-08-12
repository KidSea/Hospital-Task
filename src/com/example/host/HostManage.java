package com.example.host;

/**
 * ������ַ�������
 */
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class HostManage extends JFrame {

	// ����ؼ�
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

	// ���캯�������н����ʼ��
	public HostManage() {
		// TODO Auto-generated constructor stub
		initData();

		jp = new JPanel();

		// ��ʼ����ť
		jb1 = new JButton("�鿴����");
		jb2 = new JButton("ɾ��");
		jb3 = new JButton("����������Ϣ");

		// �Ѹ�����ť���뵽jp
		jp.add(jb1);
		jp.add(jb2);
		jp.add(jb3);

		// ����һ������ģ�Ͷ���
		hm = new HostModel();
		String[] paras = { "1" };
		hm.queryHost("select * from hosadd where 1=?", paras);

		// ��ʼ��Jtable
		jt = new JTable(hm);

		// ��ʼ��jsp
		jsp = new JScrollPane(jt);

		// ��������
		this.add(jsp);
		this.add(jp, "South");

		this.setSize(500, 500);
		this.setTitle("������ַ����");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);

	}

	private void initData() {
		// TODO Auto-generated method stub

	}
}
