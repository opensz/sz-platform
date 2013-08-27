package org.sz.core.sms.impl;

import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.sz.core.sms.impl.ModemMessageOperator;
import org.sz.core.sms.impl.ModemMessagePort;

public class ModemMessageOperator {
	private static ModemMessageOperator instance;
	private static ModemMessagePort messagePort;
	private static Lock lock = new ReentrantLock();
	int portId;
	int baudrate;
	int timeOut;
	int dataBits;
	int stopBits;
	int parity;
	int funCode;
	int dataLen;
	int appendMillsec;
	int bytes;
	int frameInterval;

	private ModemMessageOperator() {
		messagePort = ModemMessagePort.getInstance();
		this.timeOut = 60;
		this.baudrate = 9600;
		this.dataBits = 8;
		this.stopBits = 1;
		this.parity = 0;
		this.funCode = 3;
		this.dataLen = 4;
		this.appendMillsec = 38;
		this.bytes = 20;
	}

	public static ModemMessageOperator getInstance() {
		if (instance == null) {
			lock.lock();
			try {
				if (instance == null)
					instance = new ModemMessageOperator();
			} catch (Exception ex) {
				System.out.println("[sms]error:" + ex.getMessage());
			} finally {
				lock.unlock();
			}
		}
		return instance;
	}

	public boolean openPort(String portStr, int baudrate, String appName) {
		boolean rsBool = false;

		messagePort.initialize(this.timeOut, baudrate, this.dataBits,
				this.stopBits, this.parity);
		messagePort.setAppname(appName.toUpperCase());

		if (messagePort.openPort(portStr)) {
			rsBool = true;

			this.frameInterval = getFrameInterval(this.appendMillsec,
					this.bytes, baudrate);
		}
		return rsBool;
	}

	public void writeByte(char[] rs) throws Exception {
		messagePort.writePort(rs);

		Thread.sleep(this.frameInterval);
	}

	public boolean readByte(String portStr) throws Exception {
		boolean rsbool = false;
		String rsStr = "";

		char[] rsByte = messagePort.readPackData();
		if (rsByte != null) {
			for (char c : rsByte) {
				rsStr = rsStr + c;
			}
			if (rsStr.indexOf("OK") > 0) {
			
				System.out.println("找到" + portStr + ":短信模块串口");
				rsbool = true;
			}
		} else {
			System.out.println(portStr + ":不是短信模块串口");
		}

		return rsbool;
	}

	public String getRightComStr() {
		String rightCom = null;

		List<String> portList = messagePort.getAllComPorts();
		if (portList.size() > 0) {
			for (String portStr : portList) {
				if (testSms(portStr)) {
					rightCom = portStr;
					break;
				}
			}
		}
		return rightCom;
	}

	private boolean testSms(String portStr) {
		boolean rsBool = false;
		try {
			rsBool = instance.openPort(portStr, this.baudrate, "sms_port");
			String atCommand = "AT\r";
			char[] atOrder = atCommand.toCharArray();
			if (rsBool)
				instance.writeByte(atOrder);
			if (rsBool) {
				rsBool = instance.readByte(portStr);
				instance.closePort();
			}
		} catch (Exception e) {
			rsBool = false;
			e.printStackTrace();
		}
		return rsBool;
	}

	public void closePort() {
		messagePort.closePort();
	}

	public static int getFrameInterval(int appendMillsec, int dataLen,
			int baudrate) {
		int rsInt = (int) Math.ceil(12 * (dataLen + 4) * 1000 / baudrate)
				+ appendMillsec;
		return rsInt;
	}

	public static final String bytesToHexString(char[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);

		for (int i = 0; i < bArray.length; i++) {
			String sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2) {
				sb.append(0);
			}
			sb.append(sTemp.toUpperCase() + " ");
		}
		return sb.toString();
	}
}
