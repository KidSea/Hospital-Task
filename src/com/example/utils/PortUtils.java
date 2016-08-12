package com.example.utils;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;



/**
 * 这是串口的工具类
 * @author yuxuehai
 *
 */
public class PortUtils {
	
	// 定义、声明变量
	private String appName = "Java串口通信";
	private int timeout = 2000;// 定义一个打开端口的最大等待时间
	/* 检测系统中可用的通讯端口类 */
	private CommPortIdentifier commPort;
	private SerialPort serialPort;
	private OutputStream outputStream;

	public SerialPort getSerialPort() {
		return serialPort;
	}

	public void setSerialPort(SerialPort serialPort) {
		this.serialPort = serialPort;
	}

	// listPort()方法的定义
	public void listPort() {
		CommPortIdentifier cpid;
		Enumeration en = CommPortIdentifier.getPortIdentifiers();
		System.out.println("端口信息 ：" + en);
		System.out.println("端口列表如下：");
		while (en.hasMoreElements()) {
			cpid = (CommPortIdentifier) en.nextElement();
			if (cpid.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				System.out.println(cpid.getName() + ", "
						+ cpid.getCurrentOwner());
			}
		}
	}
	
	// selectPort(String portName)方法的定义
	public void selectPort(String portName) {
		this.commPort = null;
		CommPortIdentifier cpid;
		Enumeration en = CommPortIdentifier.getPortIdentifiers();
		while (en.hasMoreElements()) {
			cpid = (CommPortIdentifier) en.nextElement();
			if (cpid.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				if (cpid.getName().equals(portName)) {
					this.commPort = cpid;
				}
			}
		}
		openPort();
	}

	// openPort()方法的定义
	private void openPort() {
		try {
			serialPort = (SerialPort) commPort.open(appName, timeout);
		} catch (PortInUseException e) {
		}
		try {
			serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		} catch (UnsupportedCommOperationException e) {
		}
	}
	
	// checkPort()方法的定义
	private void checkPort() {
		if (commPort == null)
			throw new RuntimeException(
					"端口选择出错请用selectPort(String portName)方法选择正确的端口！");

		if (serialPort == null) {
			throw new RuntimeException("SerialPort 对象无效！");
		}
	}
	public void write(String message) {
		checkPort();

		try {
			outputStream = new BufferedOutputStream(
					serialPort.getOutputStream());
		} catch (IOException e) {
			throw new RuntimeException("获取端口的OutputStream出错：" + e.getMessage());
		}

		try {
			outputStream.write(message.getBytes());
			//log("信息发送成功！");
		} catch (IOException e) {
			throw new RuntimeException("向端口发送信息时出错：" + e.getMessage());
		} finally {
			try {
				outputStream.close();
			} catch (Exception e) {
			}
		}
	}
	// close()方法的定义
	public void close() {
		serialPort.close();
		serialPort = null;
		commPort = null;
	}
}
