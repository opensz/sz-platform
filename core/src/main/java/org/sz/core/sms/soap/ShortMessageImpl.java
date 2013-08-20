package org.sz.core.sms.soap;

import org.sz.core.sms.soap.ShortMessageImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.dom4j.Document;
import org.dom4j.Element;
import org.sz.core.sms.IShortMessage;
import org.sz.core.util.SpringContextHolder;
import org.sz.core.util.Dom4jUtil;

public class ShortMessageImpl implements IShortMessage {
	private static ShortMessageImpl instance;
	private static Lock lock = new ReentrantLock();
	private Properties configproperties;
	String url;
	String userID;
	String account;
	String password;
	String content;
	String sendTime;
	int sendType;
	String postFixNumber;
	int sign;

	public void initial() {
		this.url = ((String) this.configproperties.get("smsUrl"));
		this.userID = ((String) this.configproperties.get("userID"));
		this.account = ((String) this.configproperties.get("smsAccount"));
		this.password = ((String) this.configproperties.get("smsPassword"));
		this.content = ((String) this.configproperties.get("smsContent"));
		this.sendTime = "";
		this.sendType = 1;
		this.postFixNumber = "";
		this.sign = 1;
	}

	public boolean sendSms(List<String> mobiles, String message) {
		initial();
		String envelop = generateEnvelop(mobiles, message);

		OutputStreamWriter outputStreamWriter = null;
		BufferedReader bufferedReader = null;
		try {
			URL uRL = new URL(this.url);
			URLConnection conn = uRL.openConnection();
			conn.setDoOutput(true);
			outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());
			outputStreamWriter.write(envelop);
			outputStreamWriter.flush();

			bufferedReader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			StringBuffer response = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				response.append(line);
			}

			outputStreamWriter.close();
			bufferedReader.close();
			Document document = Dom4jUtil.loadXml(response.toString());
			Element root = document.getRootElement();
			Element body = root.element("Body");
			Element directSendResponse = body.element("directSendResponse");
			Element out = directSendResponse.element("out");
			String retCode = out.elementText("retCode");

			return retCode.equalsIgnoreCase("Sucess");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private String generateEnvelop(List<String> mobiles, String content) {
		StringBuffer phones = new StringBuffer();
		for (String mobile : mobiles) {
			phones.append(mobile);
			phones.append(",");
		}
		phones.deleteCharAt(phones.length() - 1);
		String wsdlStr = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://soap.sms.com\"><soapenv:Header/><soapenv:Body><soap:directSend><soap:userID>"
				+ this.userID
				+ "</soap:userID>"
				+ "<soap:account>"
				+ this.account
				+ "</soap:account>"
				+ "<soap:password>"
				+ this.password
				+ "</soap:password>"
				+ "<soap:phones>"
				+ phones
				+ "</soap:phones>"
				+ "<soap:content>"
				+ content
				+ "</soap:content>"
				+ "<soap:sendTime>"
				+ this.sendTime
				+ "</soap:sendTime>"
				+ "<soap:sendType>"
				+ this.sendType
				+ "</soap:sendType>"
				+ "<soap:postFixNumber>"
				+ this.postFixNumber
				+ "</soap:postFixNumber>"
				+ "<soap:sign>"
				+ this.sign
				+ "</soap:sign>"
				+ "</soap:directSend>"
				+ "</soapenv:Body>" + "</soapenv:Envelope>";

		return wsdlStr;
	}

	public static ShortMessageImpl getInstance() {
		if (instance == null) {
			lock.lock();
			try {
				if (instance == null)
					instance = new ShortMessageImpl();
				instance.setConfigproperties((Properties) SpringContextHolder
						.getBean("configproperties"));
			} finally {
				lock.unlock();
			}
		}
		return instance;
	}

	public void setConfigproperties(Properties configproperties) {
		this.configproperties = configproperties;
	}
}
