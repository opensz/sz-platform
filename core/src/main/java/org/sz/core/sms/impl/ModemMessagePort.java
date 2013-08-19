 package org.sz.core.sms.impl;
 
 import gnu.io.CommPortIdentifier;
 import gnu.io.PortInUseException;
 import gnu.io.SerialPort;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.io.PrintStream;
 import java.util.ArrayList;
 import java.util.Enumeration;
 import java.util.List;
 import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.sz.core.sms.impl.ModemMessagePort;
 
 public class ModemMessagePort
 {
   public static final int PACKET_LENGTH = 500;
   private static ModemMessagePort port;
   private static Lock lock = new ReentrantLock();
   SerialPort serialPort;
   CommPortIdentifier identifier;
   String PortName;
   OutputStream out;
   InputStream in;
   String appname = "SerialBean";
   int timeOut;
   int baudrate;
   int dataBits;
   int stopBits;
   int parity;
 
   public static ModemMessagePort getInstance()
   {
     if (port == null) {
       lock.lock();
       try {
         if (port == null)
           port = new ModemMessagePort();
       } finally {
         lock.unlock();
       }
     }
     return port;
   }
 
   public void setAppname(String appname)
   {
     this.appname = appname;
   }
 
   public void initialize(int timeOut, int baudrate, int dataBits, int stopBits, int parity)
   {
     this.timeOut = timeOut;
     this.baudrate = baudrate;
     this.dataBits = dataBits;
     this.stopBits = stopBits;
     this.parity = parity;
   }
 
   public boolean openPort(String portName)
   {
     boolean rsBool = false;
     this.PortName = portName;
     try
     {
       this.identifier = getCommPort();
       if (this.identifier != null)
       {
         if (!this.identifier.isCurrentlyOwned())
         {
           this.serialPort = ((SerialPort)this.identifier.open(this.appname, this.timeOut));
 
           this.in = this.serialPort.getInputStream();
           this.out = this.serialPort.getOutputStream();
 
           this.serialPort.setSerialPortParams(this.baudrate, this.dataBits, this.stopBits, this.parity);
           this.serialPort.setDTR(true);
           this.serialPort.setRTS(true);
           rsBool = true;
         }
       }
     }
     catch (PortInUseException e)
     {
     }
     catch (Exception e) {
     }
     return rsBool;
   }
 
   public CommPortIdentifier getCommPort()
     throws Exception
   {
     CommPortIdentifier portIdRs = null;
     portIdRs = CommPortIdentifier.getPortIdentifier(this.PortName);
     return portIdRs;
   }
 
   public char[] readPackData()
     throws Exception
   {
     byte[] readBuffer = new byte[500];
     char[] msgPack = null;
     int numBytes = 0;
     while (this.in.available() > 0) {
       numBytes = this.in.read(readBuffer);
       msgPack = null;
       msgPack = new char[numBytes];
       for (int i = 0; i < numBytes; i++) {
         msgPack[i] = (char)(readBuffer[i] & 0xFF);
       }
     }
     return msgPack;
   }
 
   public void writePort(char[] bytes)
     throws IOException
   {
     for (char b : bytes)
       writePort(b);
   }
 
   public void writePort(char b)
     throws IOException
   {
     this.out.write(b);
     this.out.flush();
   }
 
   public void closePort()
   {
     if (this.out != null)
       try {
         this.out.close();
         this.in.close();
         this.out = null;
         this.in = null;
       }
       catch (IOException e)
       {
       }
     if (this.serialPort != null) {
       this.serialPort.close();
       this.serialPort = null;
     }
   }
 
   public List<String> getAllComPorts()
   {
     List comList = new ArrayList();
     System.out.println("[sms]准备获取所有端口…");
     Enumeration en = CommPortIdentifier.getPortIdentifiers();
     CommPortIdentifier portIdRs = null;
     while (en.hasMoreElements()) {
       portIdRs = (CommPortIdentifier)en.nextElement();
       if (portIdRs.getPortType() == 1) {
         comList.add(portIdRs.getName());
       }
     }
     System.out.println("[sms]获取到:" + comList.size() + "个端口");
     return comList;
   }
 }

