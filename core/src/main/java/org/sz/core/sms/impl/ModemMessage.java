package org.sz.core.sms.impl;

import org.sz.core.sms.impl.ModemMessage;
import org.sz.core.sms.impl.ModemMessageOperator;

import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.smslib.AGateway.Protocols;
import org.smslib.GatewayException;
import org.smslib.Message.MessageEncodings;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;
import org.sz.core.sms.IShortMessage;

public class ModemMessage implements IShortMessage {
	private static ModemMessage instance = null;

	private static Lock lock = new ReentrantLock();

	private boolean serviceHasOpen = false;

	private Service srv = null;
	private SerialModemGateway gateway;
	private static String smsGroup = "smsgruop";

	public static ModemMessage getInstance() {
		if (instance == null) {
			lock.lock();
			try {
				if (instance == null)
					instance = new ModemMessage();
			} finally {
				lock.unlock();
			}
		}
		return instance;
	}

	private boolean initial(String com, int baudRate, String pin) {
		boolean rsbool = true;
		this.srv = new Service();
		this.gateway = new SerialModemGateway("SMSLINK", com, baudRate, "", "");
		this.gateway.setOutbound(true);
		this.gateway.setInbound(true);
		this.gateway.setProtocol(Protocols.PDU);
		this.gateway.setSimPin(pin);
		try {
			this.srv.addGateway(this.gateway);
		} catch (GatewayException e) {
			rsbool = false;
			e.printStackTrace();
		}
		if (rsbool)
			rsbool = startService();
		return rsbool;
	}

	private boolean sendMessage(List<String> phoneList, String message) {
		boolean rsbool = true;

		for (String phone : phoneList) {
			this.srv.addToGroup(smsGroup, phone);
		}
		OutboundMessage msg = new OutboundMessage(smsGroup, message);
		msg.setEncoding(MessageEncodings.ENCUCS2);
		try {
			this.srv.sendMessage(msg);

			for (String phone : phoneList)
				this.srv.removeFromGroup(smsGroup, phone);
		} catch (Exception e) {
			rsbool = false;
			e.printStackTrace();
		}
		return rsbool;
	}

	private boolean startService() {
		boolean rsbool = true;
		try {
			this.srv.startService();
			this.srv.createGroup(smsGroup);
		} catch (Exception e) {
			rsbool = false;
			e.printStackTrace();
		}
		return rsbool;
	}

	public boolean stopService() {
		boolean rsbool = true;
		try {
			if (this.srv != null) {
				this.srv.stopService();
				this.serviceHasOpen = false;
			}
		} catch (Exception e) {
			rsbool = false;
			e.printStackTrace();
		}
		return rsbool;
	}

	public boolean sendSms(List<String> mobiles, String message) {
		if (this.serviceHasOpen)
			return sendMessage(mobiles, message);
		String comStr = ModemMessageOperator.getInstance().getRightComStr();
		if (comStr == null)
			System.out.println("[SMS]未能获取到可以发送短信的串口。");
		System.out.println("[SMS]开始使用串口:" + comStr + "发送短信。");
		if (comStr != null) {
			if (initial(comStr, 9600, "0000")) {
				this.serviceHasOpen = true;
				return sendMessage(mobiles, message);
			}
			return false;
		}
		return false;
	}
}
