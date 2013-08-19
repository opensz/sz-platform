 package org.sz.core.web.tag;
 
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.jsp.JspTagException;
 import javax.servlet.jsp.JspWriter;
 import javax.servlet.jsp.PageContext;
 import javax.servlet.jsp.tagext.BodyTagSupport;
 
 public class StyleTag extends BodyTagSupport
 {
   private String href;
 
   public String getHref()
   {
     return this.href;
   }
 
   public void setHref(String href) {
     this.href = href;
   }
 
   public int doStartTag() throws JspTagException {
     return 2;
   }
 
   private String getOutput() throws Exception
   {
     HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
     StringBuffer content = new StringBuffer("<link name=\"styleTag\" rel=\"stylesheet\" type=\"text/css\" href=\"");
     content.append(request.getContextPath());
     content.append("/styles/");
 
     content.append("default/css");
 
     content.append("/");
     content.append(this.href);
     content.append("\"></link>");
 
     return content.toString();
   }
 
   public int doEndTag() throws JspTagException
   {
     try
     {
       String str = getOutput();
       this.pageContext.getOut().print(str);
     } catch (Exception e) {
       throw new JspTagException(e.getMessage());
     }
     return 0;
   }
 }

