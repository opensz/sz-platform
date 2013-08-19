 package org.sz.core.web.filter;
 
  import java.io.IOException;
 import javax.servlet.Filter;
 import javax.servlet.FilterChain;
 import javax.servlet.FilterConfig;
 import javax.servlet.ServletException;
 import javax.servlet.ServletRequest;
 import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.sz.core.web.util.RequestUtil;
 
 public class AopFilter
   implements Filter
 {
   public void init(FilterConfig filterConfig)
     throws ServletException
   {
   }
 
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
     throws IOException, ServletException
   {
     RequestUtil.setHttpServletRequest((HttpServletRequest)request);
     chain.doFilter(request, response);
   }
 
   public void destroy()
   {
   }
 }

