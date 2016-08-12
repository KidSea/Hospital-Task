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
	// 定义组件
	JPanel jp1, jp2, jp3;
	JButton jb1, jb2, jb3;
	JLabel jl;

	TextArea jta;
	JScrollPane jsp;

	JButton jb4, jb5, jb6, jb7;

	JTable jt1, jt2;

	// 定义变量
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
		/* 定义窗体适配器的关闭按钮功能 */
		mainUi.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// mainUi.pack();
	}

	public MainUi() {
		// TODO Auto-generated constructor stub
		// 初始化组件
		jp1 = new JPanel();

		jb1 = new JButton("选择端口");
		jb1.addActionListener(this);
		jb2 = new JButton("打开端口");
		jb2.addActionListener(this);
		jb3 = new JButton("关闭端口");
		jb3.addActionListener(this);

		jl = new JLabel("下面的文本框显示接收到的数据");

		jp1.add(jb1);
		jp1.add(jb2);
		jp1.add(jb3);

		jp1.add(jl);

		jp2 = new JPanel();

		jta = new TextArea();
		jsp = new JScrollPane(jta);
		jp2.add(jsp);

		jp3 = new JPanel();

		jb4 = new JButton("主机地址管理");
		jb4.addActionListener(this);
		jb5 = new JButton("从机地址管理");
		jb5.addActionListener(this);
		jb6 = new JButton("清屏");
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
		this.setTitle("主控制界面");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// 判断是哪个按钮按下
		if (e.getSource() == jb1) {
			System.out.println("用户想选择端口");
			SelectDialog dialog = new SelectDialog(this, "选择端口", true);
			select_Port = dialog.port;
			System.out.println(select_Port);
		} else if (e.getSource() == jb2) {

			System.out.println("用户想开启端口");
			// System.out.println(select_Port);
			portUtils = new PortUtils();
			portUtils.listPort();

			portUtils.selectPort(select_Port);
			jl.setText("已打开端口" + select_Port + "正在接收数据.....");
			serialPort = portUtils.getSerialPort();
			/* 设置串口监听器 */
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
							/* 从线路上读取数据流 */
							int len = inputStream.available();
							byte[] readBuffer = new byte[len];
							while (inputStream.available() > 0) {

								inputStream.read(readBuffer);

							} // while end
							count++;
							str = new String(readBuffer);
							save_str += str;

							/* 接收到的数据存放到文本区中 */
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
			/* 侦听到串口有数据,触发串口事件 */
			serialPort.notifyOnDataAvailable(true);
			readThread = new Thread(this);
			readThread.start(); // 线程负责每接收一次数据休眠20秒钟
		} else if (e.getSource() == jb3) {
			System.out.println("用户想关闭端口");
			portUtils.close();
			jl.setText("下面的文本框显示接收到的数据");
		} else if (e.getSource() == jb4) {
			System.out.println("用户想打开主机地址管理");
			hostdialog = new HostDialog(this, "主机地址管理", false);
			jt1 = hostdialog.getJt();
			is_upd1 = true;
			/* 定义窗体适配器的关闭按钮功能 */
			hostdialog.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					hostdialog.dispose();
				}
			});
			hostdialog.pack();
		} else if (e.getSource() == jb5) {
			System.out.println("用户想打开从机地址管理");
			bdevicedilog = new BdeviceDialog(this, "从机地址管理", false);
			jt2 = bdevicedilog.getJt();
			is_upd2 = true;
			/* 定义窗体适配器的关闭按钮功能 */
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
		// 先将接收到的数据拆分
		strArray = res.split("#");
		// 判断是主机地址还是从机地址
		if (strArray[0].equals("1")) {
			hos_count++;
			AddSqlData sqlData = new AddSqlData(1, strArray[1]);
			sqlData.setHos_count(hos_count);
			sqlData.addSqlData();
			if (is_upd1) {
				// 构建新的数据模型类，并更新
				HostModel hm = new HostModel();
				String[] paras = { "1" };
				hm.queryHost("select * from hosadd where 1=?", paras);
				// 更新Jtable
				jt1.setModel(hm);
			}
		} else if (strArray[0].equals("2")) {
			bd_count++;
			AddSqlData sqlData = new AddSqlData(2, strArray[1]);
			sqlData.setBd_count(bd_count);
			sqlData.addSqlData();
			if (is_upd2) {
				// 构建新的数据模型类，并更新
				BdeviceModel bd = new BdeviceModel();
				String[] paras = { "1" };
				bd.queryBd("select * from bdadd where 1=?", paras);
				// 更新Jtable
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
