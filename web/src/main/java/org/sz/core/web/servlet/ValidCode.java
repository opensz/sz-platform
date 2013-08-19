 package org.sz.core.web.servlet;
 
 import com.sun.jimi.core.Jimi;
 import com.sun.jimi.core.JimiException;
 import java.awt.Color;
 import java.awt.Font;
 import java.awt.Graphics;
 import java.awt.image.BufferedImage;
 import java.io.IOException;
 import java.util.Random;
 import javax.servlet.ServletException;
 import javax.servlet.ServletOutputStream;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 
 public class ValidCode extends HttpServlet
 {
   private static final long serialVersionUID = 1L;
   public static String SessionName_Randcode = "randcode";
 
   public void destroy()
   {
     super.destroy();
   }
 
   public void doGet(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
   {
     response.setHeader("Pragma", "No-cache");
     response.setHeader("Cache-Control", "no-cache");
     response.setDateHeader("Expires", 0L);
     response.setContentType("image/jpeg");
 
     int width = 60; int height = 20;
     BufferedImage image = new BufferedImage(width, height, 1);
 
     Graphics g = image.getGraphics();
 
     Random random = new Random();
 
     g.setColor(getRandColor(155, 254));
 
     g.fillRect(0, 0, width, height);
 
     g.setFont(new Font("Times New Roman", 0, 18));
 
     g.setColor(getRandColor(160, 220));
     for (int i = 0; i < 155; i++)
     {
       int x = random.nextInt(width);
       int y = random.nextInt(height);
       int xl = random.nextInt(12);
       int yl = random.nextInt(12);
       g.drawLine(x, y, x + xl, y + yl);
     }
 
     String sRand = "";
     for (int i = 0; i < 4; i++)
     {
       String rand = String.valueOf(random.nextInt(10));
       sRand = sRand + rand;
 
       g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
       g.drawString(rand, 13 * i + 6, 16);
     }
 
     request.getSession().setAttribute(SessionName_Randcode, sRand);
 
     g.dispose();
 
     ServletOutputStream os = response.getOutputStream();
     try
     {
       Jimi.putImage("image/jpeg", image, os);
     } catch (JimiException e) {
       e.printStackTrace();
     }
     os.flush();
     os.close();
     os = null;
     response.flushBuffer();
   }
 
   private Color getRandColor(int fc, int bc)
   {
     Random random = new Random();
     if (fc > 255)
       fc = 255;
     if (bc > 255)
       bc = 255;
     int r = fc + random.nextInt(bc - fc);
     int g = fc + random.nextInt(bc - fc);
     int b = fc + random.nextInt(bc - fc);
     return new Color(r, g, b);
   }
 
   public void doPost(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
   {
   }
 
   public void init()
     throws ServletException
   {
   }
 }

