 package org.sz.core.util;
 
 import org.sz.core.util.ImageUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
 import com.sun.image.codec.jpeg.JPEGEncodeParam;
 import com.sun.image.codec.jpeg.JPEGImageEncoder;
 import java.awt.AlphaComposite;
 import java.awt.Color;
 import java.awt.Graphics2D;
 import java.awt.Image;
 import java.awt.Rectangle;
 import java.awt.Toolkit;
 import java.awt.image.BufferedImage;
 import java.io.ByteArrayInputStream;
 import java.io.ByteArrayOutputStream;
 import java.io.File;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.util.Iterator;
 import javax.imageio.ImageIO;
 import javax.imageio.ImageReadParam;
 import javax.imageio.ImageReader;
 import javax.imageio.stream.ImageInputStream;
 import javax.swing.ImageIcon;
 import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
 public class ImageUtil
 {
   public static Log log = LogFactory.getLog(ImageUtil.class);
 
   public static Image loadImage(byte[] imagedata)
   {
     Image image = Toolkit.getDefaultToolkit().createImage(imagedata);
     return image;
   }
 
   public static Image loadImage(String filename)
   {
     return Toolkit.getDefaultToolkit().getImage(filename);
   }
 
   public static BufferedImage loadImage(File file) {
     BufferedImage bufferedImage = null;
     try {
       bufferedImage = ImageIO.read(file);
     } catch (IOException e) {
       e.printStackTrace();
       throw new RuntimeException(e);
     }
     return bufferedImage;
   }
 
   public static ImageReader getImageReader(InputStream is, String formatName) throws IOException {
     Iterator readers = ImageIO.getImageReadersByFormatName(formatName);
 
     ImageReader reader = (ImageReader)readers.next();
     ImageInputStream iis = null;
     try {
       iis = ImageIO.createImageInputStream(is);
     } catch (IOException e) {
       e.printStackTrace();
       throw new RuntimeException(e);
     }
     reader.setInput(iis, true);
     return reader;
   }
 
   public static ImageReader getImageReader(File file) {
     String formatName = getFileSuffix(file);
     Iterator readers = ImageIO.getImageReadersByFormatName(formatName);
 
     ImageReader reader = (ImageReader)readers.next();
     ImageInputStream iis = null;
     try {
       iis = ImageIO.createImageInputStream(file);
     } catch (IOException e) {
       e.printStackTrace();
       throw new RuntimeException(e);
     }
     reader.setInput(iis, true);
     return reader;
   }
 
   private static String getFileSuffix(File file)
   {
     String fileName = file.getName();
     int index = fileName.indexOf(".");
     String formatName = fileName.substring(index + 1);
     return formatName;
   }
 
   public static void cutImage(int x, int y, int width, int height, File file, File output)
   {
     String formatName = getFileSuffix(file);
     Iterator readers = ImageIO.getImageReadersByFormatName(formatName);
 
     ImageReader reader = (ImageReader)readers.next();
     ImageInputStream iis = null;
     try {
       iis = ImageIO.createImageInputStream(file);
     } catch (IOException e) {
       e.printStackTrace();
       throw new RuntimeException(e);
     }
     reader.setInput(iis, true);
     ImageReadParam param = reader.getDefaultReadParam();
     Rectangle sourceRegion = new Rectangle(x, y, width, height);
     param.setSourceRegion(sourceRegion);
     try {
       BufferedImage bufferedImage = reader.read(0, param);
       ImageIO.write(bufferedImage, getFileSuffix(file), output);
     } catch (IOException e) {
       e.printStackTrace();
       throw new RuntimeException(e);
     }
   }
 
   public static InputStream cutImage(int x, int y, int width, int height, ImageReader reader)
   {
     ImageReadParam param = reader.getDefaultReadParam();
     Rectangle sourceRegion = new Rectangle(x, y, width, height);
     param.setSourceRegion(sourceRegion);
     try {
       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       BufferedImage bufferedImage = reader.read(0, param);
       ImageIO.write(bufferedImage, "png", baos);
       ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
       return bais;
     } catch (IOException e) {
       e.printStackTrace();
                 throw new RuntimeException(e);
     }
   }
 
   public static ImageIcon getImageIcon(File file)
   {
     String filename = file.getAbsolutePath();
     return new ImageIcon(filename);
   }
 
   public static void createWaterMark(File srcFile, File waterFile, File compositeFile)
   {
     Image theImg = new ImageIcon(srcFile.getAbsolutePath()).getImage();
     Image waterImg = new ImageIcon(waterFile.getAbsolutePath()).getImage();
     int width = theImg.getWidth(null);
     int height = theImg.getHeight(null);
     int w = waterImg.getWidth(null);
     int h = waterImg.getHeight(null);
     BufferedImage bimage = new BufferedImage(width, height, 1);
     Graphics2D g = bimage.createGraphics();
     g.setBackground(Color.WHITE);
     g.drawImage(theImg, 0, 0, null);
     g.setComposite(AlphaComposite.getInstance(3, 0.4F));
     width -= w;
     height -= h;
     g.drawImage(waterImg, width, height, null);
     g.dispose();
     FileOutputStream fos = null;
     try {
       fos = new FileOutputStream(compositeFile);
       JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
       JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
       param.setQuality(80.0F, true);
       encoder.encode(bimage);
     } catch (Exception e) {
       e.printStackTrace();
       throw new RuntimeException(e);
     } finally {
       if (fos != null)
         try {
           fos.close();
         } catch (IOException e) {
           e.printStackTrace();
           throw new RuntimeException(e);
         }
     }
   }
 
   public static InputStream createRectangle(InputStream inputStream, int x, int y, int w, int h)
   {
     BufferedImage bimage = null;
     try {
       bimage = ImageIO.read(inputStream);
     } catch (IOException e) {
       e.printStackTrace();
       throw new RuntimeException(e);
     }
     Graphics2D g = bimage.createGraphics();
     g.setComposite(AlphaComposite.getInstance(3, 0.4F));
     g.setColor(Color.RED);
 
     g.drawRect(x, y, w, h);
     g.dispose();
     try {
       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       ImageIO.write(bimage, "PNG", baos);
       ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
       return bais;
     } catch (Exception e) {
       e.printStackTrace();
                throw new RuntimeException(e);
     }
   }
 
   public static void createRectangle(File srcFile, int x, int y)
   {
     File compositeFile = new File("e:\\temp.JPG");
     Image theImg = new ImageIcon(srcFile.getAbsolutePath()).getImage();
     int width = theImg.getWidth(null);
     int height = theImg.getHeight(null);
     BufferedImage bimage = new BufferedImage(width, height, 1);
     Graphics2D g = bimage.createGraphics();
     g.setBackground(Color.WHITE);
     g.drawImage(theImg, 0, 0, null);
     g.setComposite(AlphaComposite.getInstance(3, 0.4F));
     g.setColor(Color.GREEN);
     int w = 91;
     int h = 54;
 
     g.drawRect(x, y, w, h);
     g.dispose();
     FileOutputStream fos = null;
     try {
       fos = new FileOutputStream(compositeFile);
       JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
       JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
       param.setQuality(80.0F, true);
       encoder.encode(bimage);
     } catch (Exception e) {
       e.printStackTrace();
       throw new RuntimeException(e);
     } finally {
       if (fos != null)
         try {
           fos.close();
         } catch (IOException e) {
           e.printStackTrace();
           throw new RuntimeException(e);
         }
     }
   }
 
   public static void main(String[] args)
     throws Exception
   {
     testCutImage();
   }
 
   public static void testCompositeFile()
   {
     File srcFile = new File("e:\\leave.png");
     File waterFile = new File("e:\\log.JPG");
     File compositeFile = new File("e:\\temp.jpg");
     createWaterMark(srcFile, waterFile, compositeFile);
   }
 
   public static void tsetReaderImageIconTime() {
     String dir = "F:\\picture\\";
     long start = System.currentTimeMillis();
     File[] files = new File(dir).listFiles();
     for (File item : files) {
       ImageIcon imageIcon = getImageIcon(item);
       int width = imageIcon.getIconWidth();
       int height = imageIcon.getIconHeight();
       log.info("图片的宽度：" + width);
       log.info("图片的高度：" + height);
     }
     long end = System.currentTimeMillis();
     log.info("所花时间：" + (end - start) / 1000L + "秒");
   }
 
   public static void testCutImage() throws IOException {
     File file = new File("e:\\vehicle_examine_info.png");
     File output = new File("e:\\vehicle_examine_info.png");
     ImageReader reader = getImageReader(file);
     int imageIndex = 0;
     int width = reader.getWidth(imageIndex) / 2;
     int height = reader.getHeight(imageIndex);
     cutImage(0, 0, width, height, file, output);
   }
 
   public static void testReaderImageTime()
     throws IOException
   {
     String dir = "F:\\picture\\";
     long start = System.currentTimeMillis();
     File[] files = new File(dir).listFiles();
     for (File item : files) {
       ImageReader reader = getImageReader(item);
       int imageIndex = 0;
       int width = reader.getWidth(imageIndex);
       int height = reader.getHeight(imageIndex);
       log.info("图片的宽度：" + width);
       log.info("图片的高度：" + height);
     }
     long end = System.currentTimeMillis();
     log.info("所花时间：" + (end - start) / 1000L + "秒");
   }
 }

