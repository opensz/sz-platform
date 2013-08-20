 package org.sz.core.web.taglib;
 
 import java.net.URLEncoder;
 import java.util.Enumeration;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.jsp.JspTagException;
 import javax.servlet.jsp.JspWriter;
 import javax.servlet.jsp.PageContext;
 import javax.servlet.jsp.tagext.BodyContent;
 import javax.servlet.jsp.tagext.BodyTagSupport;
 
 public class OrderTag extends BodyTagSupport
 {
   private static final String Asc = "ASC";
   private static final String Desc = "DESC";
   private String field = "";
   private String order = "DESC";
 
   private String ascImg = "/themes/img/commons/asc.png";
   private String descImg = "/themes/img/commons/desc.png";
 
   private String[] aryAvoid = { "sortField", "orderSeq" };
 
   public String getField()
   {
     return this.field;
   }
 
   public void setField(String field) {
     this.field = field;
   }
 
   public String getOrder() {
     return this.order;
   }
 
   public void setOrder(String order) {
     this.order = order;
   }
 
   public int doStartTag()
     throws JspTagException
   {
     return 2;
   }
 
   private String getOutput(HttpServletRequest request) throws Exception
   {
     String body = getBodyContent().getString();
     if ((this.field == null) || (this.field.equals("")))
     {
       return "<th>" + body + "</th>";
     }
     String img = "";
     String orderSeq = request.getParameter("orderSeq");
     String sortField = request.getParameter("sortField");
     if ((orderSeq == null) || (!sortField.equals(this.field)))
     {
       orderSeq = this.order;
     }
     else if (orderSeq.equals("DESC"))
     {
       orderSeq = "ASC";
     }
     else
     {
       orderSeq = "DESC";
     }
 
     if (orderSeq.equals("DESC"))
     {
       img = request.getContextPath() + this.descImg;
     }
     else
     {
       img = request.getContextPath() + this.ascImg;
     }
 
     String url = getUrl(request);
 
     String para = "sortField=" + this.field + "&orderSeq=" + orderSeq;
     if (url.indexOf("?") > -1)
       url = url + "&" + para;
     else {
       url = url + "?" + para;
     }
 
     StringBuffer sb = new StringBuffer();
     sb.append("<th  >");
 
     sb.append("<a href='" + url + "'>" + body + "<span style='vertical-align:middle;'><img border='0' src='" + img + "'/></span></a>");
 
     sb.append("</th>");
     return sb.toString();
   }
 
   private String getUrl(HttpServletRequest request)
     throws Exception
   {
     StringBuffer urlThisPage = new StringBuffer();
     String url = request.getAttribute("currentPath").toString();
     if (url == null)
     {
       throw new Exception("请在控制器中设置currentPath(当前路径)!");
     }
 
     urlThisPage.append(url);
     Enumeration e = request.getParameterNames();
     String para = "";
     String values = "";
     urlThisPage.append("?");
     while (e.hasMoreElements())
     {
       para = (String)e.nextElement();
       boolean rtn = isExists(para);
       if (!rtn)
       {
         values = URLEncoder.encode(getValueByKey(request, para), "utf-8");
         urlThisPage.append(para);
         urlThisPage.append("=");
         urlThisPage.append(values);
         urlThisPage.append("&");
       }
     }
     return urlThisPage.substring(0, urlThisPage.length() - 1);
   }
 
   private boolean isExists(String key)
   {
     for (String str : this.aryAvoid)
     {
       if (key.equals(str))
         return true;
     }
     return false;
   }
 
   private String getValueByKey(HttpServletRequest request, String key)
   {
     String rtn = "";
     String[] values = request.getParameterValues(key);
     for (String str : values)
     {
       if ((str == null) || (str.trim().equals("")))
         continue;
       rtn = rtn + str + ",";
     }
 
     if (rtn.length() > 0)
       rtn = rtn.substring(0, rtn.length() - 1);
     return rtn;
   }
 
   public int doEndTag() throws JspTagException
   {
     String body = getBodyContent().getString();
     HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
     try
     {
       JspWriter writer = this.pageContext.getOut();
       String str = getOutput(request);
       this.pageContext.getOut().print(str);
     } catch (Exception e) {
       throw new JspTagException(e.getMessage());
     }
     return 0;
   }
 }

