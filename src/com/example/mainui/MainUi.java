package com.example.mainui;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.TooManyListenersException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import com.example.bdevice.BdeviceDialog;
import com.example.bdevice.BdeviceManage;
import com.example.bdevice.BdeviceModel;
import com.example.host.HostDialog;
import com.example.host.HostManage;
import com.example.host.HostModel;
import com.example.sql.AddSqlData;
import com.example.utils.PortUtils;

public class MainUi extends JFrame implements ActionListener, Runnable {
	// �������
	JPanel jp1, jp2, jp3;
	JButton jb1, jb2, jb3;
	JLabel jl;

	TextArea jta;
	JScrollPane jsp;

	JButton jb4, jb5, jb6, jb7;

	JTable jt1, jt2;

	// �������
	String select_Port = null;
	private PortUtils portUtils;
	SerialPort serialPort;
	Thread readThread;
	InputStream inputStream;
	String save_str = "";
	String[] strArray;
	int hos_count;
	int bd_count;
	int count;
	int lens;
	private HostManage manage;
	private BdeviceManage manage2;

	boolean is_upd1, is_upd2;
	private BdeviceDialog bdevicedilog;
	private HostDialog hostdialog;

	// int count=0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainUi mainUi = new MainUi();
		/* ���崰���������Ĺرհ�ť���� */
		mainUi.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// mainUi.pack();
	}

	public MainUi() {
		// TODO Auto-generated constructor stub
		// ��ʼ�����
		jp1 = new JPanel();

		jb1 = new JButton("ѡ��˿�");
		jb1.addActionListener(this);
		jb2 = new JButton("�򿪶˿�");
		jb2.addActionListener(this);
		jb3 = new JButton("�رն˿�");
		jb3.addActionListener(this);

		jl = new JLabel("������ı�����ʾ���յ�������");

		jp1.add(jb1);
		jp1.add(jb2);
		jp1.add(jb3);

		jp1.add(jl);

		jp2 = new JPanel();

		jta = new TextArea();
		jsp = new JScrollPane(jta);
		jp2.add(jsp);

		jp3 = new JPanel();

		jb4 = new JButton("������ַ����");
		jb4.addActionListener(this);
		jb5 = new JButton("�ӻ���ַ����");
		jb5.addActionListener(this);
		jb6 = new JButton("����");
		jb6.addActionListener(this);
		// jb6 = new JButton("");
		jp3.add(jb4);
		jp3.add(jb5);
		jp3.add(jb6);
		// jp3.add(jb6);

		this.add(jp1, "North");
		this.add(jp2, "Center");
		this.add(jp3, "South");

		this.setSize(550, 300);
		this.setTitle("�����ƽ���");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// �ж����ĸ���ť����
		if (e.getSource() == jb1) {
			System.out.println("�û���ѡ��˿�");
			SelectDialog dialog = new SelectDialog(this, "ѡ��˿�", true);
			select_Port = dialog.port;
			System.out.println(select_Port);
		} else if (e.getSource() == jb2) {

			System.out.println("�û��뿪���˿�");
			// System.out.println(select_Port);
			portUtils = new PortUtils();
			portUtils.listPort();

			portUtils.selectPort(select_Port);
			jl.setText("�Ѵ򿪶˿�" + select_Port + "���ڽ�������.....");
			serialPort = portUtils.getSerialPort();
			/* ���ô��ڼ����� */
			try {
				serialPort.addEventListener(new SerialPortEventListener() {
					private String str;

					@Override
					public void serialEvent(SerialPortEvent arg0) {
						// TODO Auto-generated method stub
						// try {
						// serialPort.setSerialPortParams(9600,
						// SerialPort.DATABITS_8,
						// SerialPort.STOPBITS_1,
						// SerialPort.PARITY_NONE);
						// } catch (UnsupportedCommOperationException e) {
						// e.printStackTrace();
						// }
						try {
							inputStream = serialPort.getInputStream();
						} catch (IOException e) {
							e.printStackTrace();
						}
						try {
							/* ����·�϶�ȡ������ */
							int len = inputStream.available();
							byte[] readBuffer = new byte[len];
							while (inputStream.available() > 0) {

								inputStream.read(readBuffer);

							} // while end
							count++;
							str = new String(readBuffer);
							save_str += str;

							/* ���յ������ݴ�ŵ��ı����� */
							jta.append(str + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
						if ((count > 0) && (str.length() == 7)) {
							count = 0;
							Judge_Add(save_str);
							save_str = "";
						}else if ((count == 2)&& (save_str.length() >= 7)) {
							count = 0;
							Judge_Add(save_str);
							save_str = "";

						}else if (count == 3 ) {
							count = 0;
							Judge_Add(save_str);
							save_str = "";

						}
					} // serialEvent() end

				});
			} catch (TooManyListenersException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			/* ����������������,���������¼� */
			serialPort.notifyOnDataAvailable(true);
			readThread = new Thread(this);
			readThread.start(); // �̸߳���ÿ����һ����������20����
		} else if (e.getSource() == jb3) {
			System.out.println("�û���رն˿�");
			portUtils.close();
			jl.setText("������ı�����ʾ���յ�������");
		} else if (e.getSource() == jb4) {
			System.out.println("�û����������ַ����");
			hostdialog = new HostDialog(this, "������ַ����", false);
			jt1 = hostdialog.getJt();
			is_upd1 = true;
			/* ���崰���������Ĺرհ�ť���� */
			hostdialog.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					hostdialog.dispose();
				}
			});
			hostdialog.pack();
		} else if (e.getSource() == jb5) {
			System.out.println("�û���򿪴ӻ���ַ����");
			bdevicedilog = new BdeviceDialog(this, "�ӻ���ַ����", false);
			jt2 = bdevicedilog.getJt();
			is_upd2 = true;
			/* ���崰���������Ĺرհ�ť���� */
			bdevicedilog.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					bdevicedilog.dispose();
				}
			});
			bdevicedilog.pack();
		}else if (e.getSource() == jb6) {
			jta.setText("");
		}
	}

	protected void Judge_Add(String res) {
		// TODO Auto-generated method stub
		// �Ƚ����յ������ݲ��
		strArray = res.split("#");
		// �ж���������ַ���Ǵӻ���ַ
		if (strArray[0].equals("1")) {
			hos_count++;
			AddSqlData sqlData = new AddSqlData(1, strArray[1]);
			sqlData.setHos_count(hos_count);
			sqlData.addSqlData();
			if (is_upd1) {
				// �����µ�����ģ���࣬������
				HostModel hm = new HostModel();
				String[] paras = { "1" };
				hm.queryHost("select * from hosadd where 1=?", paras);
				// ����Jtable
				jt1.setModel(hm);
			}
		} else if (strArray[0].equals("2")) {
			bd_count++;
			AddSqlData sqlData = new AddSqlData(2, strArray[1]);
			sqlData.setBd_count(bd_count);
			sqlData.addSqlData();
			if (is_upd2) {
				// �����µ�����ģ���࣬������
				BdeviceModel bd = new BdeviceModel();
				String[] paras = { "1" };
				bd.queryBd("select * from bdadd where 1=?", paras);
				// ����Jtable
				jt2.setModel(bd);
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

}
