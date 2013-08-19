 package org.sz.core.web.util;
 
  import java.util.LinkedHashMap;
 import java.util.Map;
 import javax.servlet.ServletContext;
 import org.springframework.beans.BeansException;
 import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.sz.core.model.OnlineUser;
 
 public class AppUtil extends org.sz.core.util.AppUtil
   implements ApplicationContextAware
 {
   private static ServletContext servletContext;
   private static Map<Long, OnlineUser> onlineUsers = new LinkedHashMap();
 
   public static void init(ServletContext _servletContext)
   {
     servletContext = _servletContext;
   }
 
 
   public static ServletContext getServletContext()
     throws Exception
   {
     return servletContext;
   }
 
 
   public static String getAppAbsolutePath()
   {
     return servletContext.getRealPath("/");
   }
 
   public static String getRealPath(String path)
   {
     return servletContext.getRealPath(path);
   }
 
   public static Map<Long, OnlineUser> getOnlineUsers() {
     return onlineUsers;
   }
 }

