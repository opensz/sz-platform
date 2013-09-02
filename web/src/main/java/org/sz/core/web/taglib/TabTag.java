 package org.sz.core.web.taglib;
 
  import java.io.IOException;
 import java.io.InputStream;
 import java.util.Enumeration;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.jsp.JspWriter;
 import javax.servlet.jsp.PageContext;
 import javax.servlet.jsp.tagext.TagSupport;
 import org.dom4j.Document;
import org.dom4j.Element;
import org.sz.core.util.Dom4jUtil;
import org.sz.core.util.FileUtil;
import org.sz.core.util.StringUtil;
 
 public class TabTag extends TagSupport
 {
   private int curTab = 1;
 
   private String tabName = "";
 
   public int doEndTag() {
     try {
       String html = getTabHtml();
       this.pageContext.getOut().print(html);
     }
     catch (IOException e) {
       e.printStackTrace();
     }
     return 0;
   }
 
   private String getTabHtml() throws IOException {
     InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("conf/tabConfig.xml");
     String xml = FileUtil.inputStream2String(is, "utf-8");
     Document document = Dom4jUtil.loadXml(xml);
     Element root = document.getRootElement();
 
     Element tabEl = (Element)root.selectSingleNode("tab[@name='" + this.tabName + "']");
     List list = tabEl.elements();
     String parameter = getParameter();
     StringBuffer sb = new StringBuffer();
     sb.append("<div class='l-tab-links'><ul style='left: 0px; '>");
     for (int i = 0; i < list.size(); i++) {
       Element el = (Element)list.get(i);
       String title = el.attributeValue("title");
       String url = el.attributeValue("url");
       if (StringUtil.isNotEmpty(parameter)) {
         url = url + "?" + parameter;
       }
       boolean isCurrent = this.curTab == i;
       String html = getTabHtml(title, i, isCurrent, url);
       sb.append(html);
     }
     sb.append("</ul></div>");
     return sb.toString();
   }
 
   private String getTabHtml(String tilte, int tabId, boolean isCurrentTab, String url)
   {
     HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
     String ctx = request.getContextPath();
     String classes = isCurrentTab ? "class='l-selected'" : "";
     return "<li " + classes + "  tabid='tab" + tabId + "'><a href=" + ctx + url + ">" + tilte + "</a><div class='l-tab-links-item-left'></div><div class='l-tab-links-item-right'></div></li>";
   }
 
   private String getParameter()
   {
     HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
     Enumeration enumeration = request.getParameterNames();
     StringBuffer sb = new StringBuffer();
     while (enumeration.hasMoreElements()) {
       String parameter = enumeration.nextElement().toString();
       String value = request.getParameter(parameter);
       sb.append(parameter + "=" + value + "&");
     }
     String rtn = sb.toString();
     if (rtn.length() > 0)
       rtn = rtn.substring(0, rtn.length() - 1);
     return rtn;
   }
 
   public String getTabName()
   {
     return this.tabName;
   }
 
   public void setTabName(String tabName) {
     this.tabName = tabName;
   }
 
   public int getCurTab() {
     return this.curTab;
   }
 
   public void setCurTab(int index) {
     this.curTab = (index - 1);
   }
 }

