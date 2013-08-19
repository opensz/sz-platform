package org.sz.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.sz.core.util.CertUtil;

public class CertUtil
{
  public static Log logger = LogFactory.getLog(CertUtil.class);

  private static final char[] HEXDIGITS = "0123456789abcdef".toCharArray();

  public static void main(String[] args)
  {
    File file = get("smtp.gmail.com", 465);
    System.out.println(file.getAbsolutePath());
  }

  public static File get(String host, int port) {
    InputStream in = null;
    SSLSocket socket = null;
    OutputStream out = null;
    File file = null;
    try {
      char[] passphrase = "changeit".toCharArray();
      file = new File("jssecacerts");
      if (!file.isFile()) {
        char SEP = File.separatorChar;
        File dir = new File(new StringBuilder().append(System.getProperty("java.home")).append(SEP).append("lib").append(SEP).append("security").toString());

        file = new File(dir, "jssecacerts");
        if (!file.isFile()) {
          file = new File(dir, "cacerts");
        }

      }

      in = new FileInputStream(file);
      KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
      ks.load(in, passphrase);

      SSLContext context = SSLContext.getInstance("TLS");
      TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      tmf.init(ks);
      X509TrustManager defaultTrustManager = (X509TrustManager)tmf.getTrustManagers()[0];
      SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
      context.init(null, new TrustManager[] { tm }, null);
      SSLSocketFactory factory = context.getSocketFactory();

      socket = (SSLSocket)factory.createSocket(host, port);

      socket.setSoTimeout(10000);
      if (socket != null) {
        socket.startHandshake();
      }

      X509Certificate[] chain = tm.chain;
      if (chain == null) {
        return null;
      }
      MessageDigest sha1 = MessageDigest.getInstance("SHA1");
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      for (int i = 0; i < chain.length; i++) {
        X509Certificate cert = chain[i];
        sha1.update(cert.getEncoded());
        md5.update(cert.getEncoded());
      }

      int k = 0;

      X509Certificate cert = chain[k];
      String alias = new StringBuilder().append(host).append("-").append(k + 1).toString();
      ks.setCertificateEntry(alias, cert);

      File cafile = new File("jssecacerts");
      out = new FileOutputStream(cafile);
      ks.store(out, passphrase);

      logger.debug(new StringBuilder().append(">>>>   Added certificate to keystore 'jssecacerts' using alias '").append(alias).append("'").toString());
      File localFile1 = cafile;
      return localFile1;
    }
    catch (SSLException e)
    {
      logger.debug(new StringBuilder().append("明文连接,javax.net.ssl.SSLException:").append(e.getMessage()).toString());
      file = null;
      return file;
    }
    catch (KeyStoreException e)
    {
      e.printStackTrace();
      file = null;
      return file;
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      file = null;
      return file;
    }
    catch (NoSuchAlgorithmException e)
    {
      e.printStackTrace();
      file = null;
      return file;
    }
    catch (SocketTimeoutException e)
    {
      logger.debug("邮件发送超时");
      file = null;
      return file;
    }
    catch (CertificateException e)
    {
      e.printStackTrace();
      file = null;
      return file;
    }
    catch (IOException e)
    {
      e.printStackTrace();
      file = null;
      return file;
    }
    catch (KeyManagementException e)
    {
      e.printStackTrace();
      return null;
    }
    finally
    {
      System.out.println("关闭连接...");
      try {
        if (in != null) in.close();
        if (socket != null) socket.close();
        if (out != null) out.close(); 
      }
      catch (IOException e) {
        e.printStackTrace();
        System.out.println("关闭连接失败!");
      }
    }
  }

  private static String toHexString(byte[] bytes)
  {
    StringBuilder sb = new StringBuilder(bytes.length * 3);
    for (int b : bytes) {
      b &= 255;
      sb.append(HEXDIGITS[(b >> 4)]);
      sb.append(HEXDIGITS[(b & 0xF)]);
      sb.append(' ');
    }
    return sb.toString();
  }
  private static class SavingTrustManager implements X509TrustManager {
    private final X509TrustManager tm;
    private X509Certificate[] chain;

    SavingTrustManager(X509TrustManager tm) {
      this.tm = tm;
    }

    public X509Certificate[] getAcceptedIssuers() {
      throw new UnsupportedOperationException();
    }

    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
    {
      throw new UnsupportedOperationException();
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
    {
      this.chain = chain;
      this.tm.checkServerTrusted(chain, authType);
    }
  }
}