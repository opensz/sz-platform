 package org.sz.core.web.filter;
 
 import java.io.IOException;
 import javax.servlet.Filter;
 import javax.servlet.FilterChain;
 import javax.servlet.FilterConfig;
 import javax.servlet.ServletException;
 import javax.servlet.ServletRequest;
 import javax.servlet.ServletResponse;
 
 public class EncodingFilter
   implements Filter
 {
   private String encoding = "UTF-8";
   private String contentType = "text/html;charset=UTF-8";
 
   public void destroy()
   {
   }
 
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
     throws IOException, ServletException
   {
     request.setCharacterEncoding(this.encoding);
     response.setCharacterEncoding(this.encoding);
     response.setContentType(this.contentType);
 
     chain.doFilter(request, response);
   }
 
   public void init(FilterConfig config)
     throws ServletException
   {
     String _encoding = config.getInitParameter("encoding");
     String _contentType = config.getInitParameter("contentType");
 
     if (_encoding != null)
       this.encoding = _encoding;
     if (_contentType != null)
       this.contentType = _contentType;
   }
 }

