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
 * ���Ǵ��ڵĹ�����
 * @author yuxuehai
 *
 */
public class PortUtils {
	
	// ���塢��������
	private String appName = "Java����ͨ��";
	private int timeout = 2000;// ����һ���򿪶˿ڵ����ȴ�ʱ��
	/* ���ϵͳ�п��õ�ͨѶ�˿��� */
	private CommPortIdentifier commPort;
	private SerialPort serialPort;
	private OutputStream outputStream;

	public SerialPort getSerialPort() {
		return serialPort;
	}

	public void setSerialPort(SerialPort serialPort) {
		this.serialPort = serialPort;
	}

	// listPort()�����Ķ���
	public void listPort() {
		CommPortIdentifier cpid;
		Enumeration en = CommPortIdentifier.getPortIdentifiers();
		System.out.println("�˿���Ϣ ��" + en);
		System.out.println("�˿��б����£�");
		while (en.hasMoreElements()) {
			cpid = (CommPortIdentifier) en.nextElement();
			if (cpid.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				System.out.println(cpid.getName() + ", "
						+ cpid.getCurrentOwner());
			}
		}
	}
	
	// selectPort(String portName)�����Ķ���
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

	// openPort()�����Ķ���
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
	
	// checkPort()�����Ķ���
	private void checkPort() {
		if (commPort == null)
			throw new RuntimeException(
					"�˿�ѡ���������selectPort(String portName)����ѡ����ȷ�Ķ˿ڣ�");

		if (serialPort == null) {
			throw new RuntimeException("SerialPort ������Ч��");
		}
	}
	public void write(String message) {
		checkPort();

		try {
			outputStream = new BufferedOutputStream(
					serialPort.getOutputStream());
		} catch (IOException e) {
			throw new RuntimeException("��ȡ�˿ڵ�OutputStream����" + e.getMessage());
		}

		try {
			outputStream.write(message.getBytes());
			//log("��Ϣ���ͳɹ���");
		} catch (IOException e) {
			throw new RuntimeException("��˿ڷ�����Ϣʱ����" + e.getMessage());
		} finally {
			try {
				outputStream.close();
			} catch (Exception e) {
			}
		}
	}
	// close()�����Ķ���
	public void close() {
		serialPort.close();
		serialPort = null;
		commPort = null;
	}
}
