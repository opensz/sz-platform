 package org.sz.platform.oa.service.desk.impl;
 
  import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.Dom4jUtil;
import org.sz.core.util.FileUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.oa.dao.desk.DesktopColumnDao;
import org.sz.platform.oa.dao.desk.DesktopLayoutcolDao;
import org.sz.platform.oa.dao.desk.DesktopMycolumnDao;
import org.sz.platform.oa.model.desk.DesktopColumn;
import org.sz.platform.oa.service.desk.DesktopColumnService;
 
 @Service("desktopColumnService")
 public class DesktopColumnServiceImpl extends BaseServiceImpl<DesktopColumn> implements DesktopColumnService
 {
 
   @Resource
   private DesktopColumnDao dao;
 
   @Resource
   private DesktopLayoutcolDao desktopLayoutcolDao;
 
   @Resource
   private DesktopMycolumnDao desktopMycolumnDao;
 
   protected IEntityDao<DesktopColumn, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public String getTemplatePath()
   {
     String templatePath = Thread.currentThread().getContextClassLoader().getResource("template/").toString();
     if (templatePath.startsWith("file:/")) {
       templatePath = templatePath.replaceFirst("file:/", "");
     }
     templatePath = templatePath.replace("/", File.separator);
     return templatePath;
   }
 
   public void delDesktopColumn(Long[] ids)
   {
     if ((ids == null) || (ids.length == 0)) return;
     for (Long id : ids)
     {
       this.desktopLayoutcolDao.delByLinkLayout(id);
       this.desktopMycolumnDao.delByLinkMycolumn(id);
     }
     delByIds(ids);
   }
 
   public boolean isExistDesktopColumn(String deskName)
   {
     DesktopColumn desktopColumn = this.dao.getByDeskName(deskName);
 
     return desktopColumn != null;
   }
 
   public void initAllTemplate(long userId)
     throws Exception
   {
     List desktopColumnList = null;
     desktopColumnList = this.dao.getAll();
     Long[] ids = new Long[desktopColumnList.size()];
     for (int i = 0; i < desktopColumnList.size(); i++) {
       DesktopColumn dc = (DesktopColumn)desktopColumnList.get(i);
       ids[i] = dc.getId();
     }
     delDesktopColumn(ids);
     addDesktopColumn(userId);
   }
 
   public void addDesktopColumn(long userId)
     throws Exception
   {
     String templatePath = getTemplatePath();
     String xml = FileUtil.readFile(templatePath + "desktop" + File.separator + "desktop.xml");
     Document document = Dom4jUtil.loadXml(xml);
     Element root = document.getRootElement();
     List<Element> list = root.elements();
     for (Element element : list) {
       String name = element.attributeValue("name");
       String serviceMethod = element.attributeValue("serviceMethod");
       String templateName = element.attributeValue("templateName");
       String templateId = element.attributeValue("templateId");
       String columnUrl = element.attributeValue("columnUrl");
       Integer isSys = Integer.valueOf(Integer.parseInt(element.attributeValue("isSys")));
       String fileName = templateId + ".ftl";
       String html = FileUtil.readFile(templatePath + "desktop" + File.separator + fileName);
       DesktopColumn desktopColumn = new DesktopColumn();
       desktopColumn.setColumnUrl(columnUrl);
       desktopColumn.setCreateBy(Long.valueOf(userId));
       desktopColumn.setCreatetime(new Date());
       desktopColumn.setHtml(html);
       desktopColumn.setName(name);
       desktopColumn.setServiceMethod(serviceMethod);
       desktopColumn.setTemplateId(templateId);
       desktopColumn.setTemplateName(templateName);
       desktopColumn.setTemplatePath(templatePath);
       desktopColumn.setIsSys(isSys);
       desktopColumn.setId(Long.valueOf(UniqueIdUtil.genId()));
       this.dao.add(desktopColumn);
     }
   }
 
   public String getNameById(long columnId)
   {
     return this.dao.getNameById(columnId);
   }
 
   public String getColumnUrlById(long columnId)
   {
     return this.dao.getColumnUrlById(columnId);
   }
   
   public DesktopColumn getByDeskName(String name){
	   return this.dao.getByDeskName(name);
   }
 }

