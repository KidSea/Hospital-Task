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
	private JFrame Mjframe;
	
	public HostDialog(JFrame owner, String title, boolean modal) {
		// TODO Auto-generated constructor stub
		super(owner, title, modal);
		Mjframe = owner; 
		initData();

		jp = new JPanel();

		// ��ʼ����ť
		jb1 = new JButton("�鿴����");
		jb1.addActionListener(this);
		jb2 = new JButton("ɾ��");
		jb2.addActionListener(this);
		//jb3 = new JButton("����������Ϣ");
		
		

		// �Ѹ�����ť���뵽jp
		jp.add(jb1);
		jp.add(jb2);
		//jp.add(jb3);

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
		//this.setTitle("������ַ����");

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
			System.out.println("�û���鿴����");
		}else if (e.getSource() == jb2) {
			System.out.println("�û���ɾ��");
			//��������ť˵���û���ɾ��
			//�õ��ôӻ���ID
			//������û�һ�ж�ûѡ���򷵻�-1
			int rowNum = jt.getSelectedRow();
			if(rowNum == -1){
				// ��ʾ
				JOptionPane.showMessageDialog(this, "ѡ��һ��");
				return;
			}
			//ɾ�����ݿ��е�һ����¼ 
			String bdId = (String) hm.getValueAt(rowNum, 0);
			//����һ��SQL���
			String sql = "delete from hosadd where ����=?";
			String [] paras ={bdId};
			HostModel temp = new HostModel();
			temp.updHost(sql, paras);
			
			//�����µ�����ģ���࣬������
		    hm = new HostModel();
		    String []paras2 = {"1"};
		    hm.queryHost("select * from hosadd where 1=?", paras2);
		    //����
		    jt.setModel(hm);
		}
	}
}
