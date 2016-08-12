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
	// �������
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
		super(owner, title, modal);// ���ø��෽��

		bdstr = (String) bd.getValueAt(rowNums, 0);

		jp1 = new JPanel();
		jl = new JLabel("��ѡ��һ������");
		jp1.add(jl);

		jp2 = new JPanel();
		jb1 = new JButton("ѡ��");
		jb1.addActionListener(this);
		jl2 = new JLabel("ѡ���������     ");
		jb2 = new JButton("ȷ��");
		jb2.addActionListener(this);
		jp2.add(jl2);
		jp2.add(jb1);
		jp2.add(jb2);

		// ����һ������ģ�Ͷ���
		hm = new HostModel();
		String[] paras = { "1" };
		hm.queryHost("select * from hosadd where 1=?", paras);
		// ��ʼ��Jtable
		jt = new JTable(hm);

		// ��ʼ��jsp
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
			// ��ȡѡȡ����
			int rowNum = this.jt.getSelectedRow();
			if (rowNum == -1) {
				// ��ʾ
				JOptionPane.showMessageDialog(this, "ѡ��һ��");
				return;
			}

			str = "";
			str = (String) hm.getValueAt(rowNum, 0);
			jl2.setText("ѡ�������:" + str);
		} else if (e.getSource() == jb2) {
			// ��һ��SQL
			// ����������
			String sql = "update bdadd set ƥ�����=? where ����=?";
			String[] paras = { str, bdstr };
			BdeviceModel bd = new BdeviceModel();
			bd.updBdevice(sql, paras);
			this.dispose();
		}

	}

}
