 package org.sz.platform.service.system.impl;
 
  import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.Dom4jUtil;
import org.sz.core.util.StringUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.dao.system.ScriptDao;
import org.sz.platform.dao.system.SysFileDao;
import org.sz.platform.model.system.Script;
import org.sz.platform.service.system.ScriptService;
 
 @Service("scriptService")
 public class ScriptServiceImpl extends BaseServiceImpl<Script> implements ScriptService
 {
 
   @Resource
   private ScriptDao dao;
 
   @Resource
   private SysFileDao sysFileDao;
 
   protected IEntityDao<Script, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public List<String> getDistinctCategory() {
     return this.dao.getDistinctCategory();
   }
 
   public String importXml(InputStream inputStream)
     throws Exception
   {
     String msg = "";
     Document doc = Dom4jUtil.loadXml(inputStream);
     Element root = doc.getRootElement();
     List<Element> itemLists = root.elements();
     if ((itemLists != null) && (itemLists.size() > 0)) {
       for (Element elm : itemLists) {
         String itemName = elm.element("name").getText();
         if (this.dao.isExistWithName(itemName).intValue() < 1) {
           Long tableId = Long.valueOf(UniqueIdUtil.genId());
           String scriptText = elm.element("content").getText();
           String memoText = elm.element("Memo").getText();
           String scriptType = elm.element("type").getText();
           Script script = new Script();
           script.setId(tableId);
           script.setName(itemName);
           script.setScript(scriptText);
           script.setMemo(memoText);
           script.setCategory(scriptType);
           this.dao.add(script);
         }
         else
         {
           msg = msg + "存在重复脚本名:" + itemName + "\n";
           return msg;
         }
       }
     }
     return msg;
   }
 
   public String exportXml(Long[] tableId)
     throws FileNotFoundException, IOException
   {
     String strXml = "";
     Document doc = DocumentHelper.createDocument();
     Element root = doc.addElement("List");
     for (int i = 0; i < tableId.length; i++)
     {
       Script script = (Script)this.dao.getById(tableId[i]);
       if (!BeanUtils.isNotEmpty(script)) {
         continue;
       }
       exportTable(script, root, "Script");
     }
 
     strXml = doc.asXML();
     return strXml;
   }
 
   private void exportTable(Script script, Element root, String nodeName)
   {
     Element elements = root.addElement(nodeName);
     elements.addCDATA("");
     if (StringUtil.isNotEmpty(script.getName())) {
       elements.addElement("name").addText(script.getName());
     }
     if (StringUtil.isNotEmpty(script.getScript())) {
       elements.addElement("content").addText(script.getScript());
     }
     if (BeanUtils.isNotEmpty(script.getCategory())) {
       elements.addElement("type").addText(script.getCategory());
     }
     if (BeanUtils.isNotEmpty(script.getMemo()))
       elements.addElement("Memo").addText(script.getMemo());
   }
 }

