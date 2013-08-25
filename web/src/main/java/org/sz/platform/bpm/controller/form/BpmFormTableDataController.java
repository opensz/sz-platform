 package org.sz.platform.bpm.controller.form;
 
  import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.engine.FreemarkEngine;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.form.BpmFormData;
import org.sz.platform.bpm.model.form.BpmFormTable;
import org.sz.platform.bpm.service.form.BpmFormTableDataService;
import org.sz.platform.bpm.service.form.BpmFormTableService;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
 
 @Controller
 @RequestMapping({"/platform/form/bpmFormTableData/"})
 public class BpmFormTableDataController extends BaseController
 {
 
   @Resource
   private BpmFormTableDataService service;
 
   @Resource
   private BpmFormTableService bpmFormTableService;
 
   @Resource
   private FreemarkEngine freemarkEngine;
 
   @RequestMapping({"list"})
   @Action(description="查看数据列表")
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="page", defaultValue="1") int page, Long tableId)
     throws Exception
   {
     BpmFormTable table = (BpmFormTable)this.bpmFormTableService.getById(tableId);
 
     Map<String,String[]> map = request.getParameterMap();
     Map params = new HashMap();
     for (Map.Entry entry : map.entrySet()) {
       String key = (String)entry.getKey();
       if ((key.startsWith("Q_")) && (!"".equals(((String[])entry.getValue())[0]))) {
         params.put(key.substring(key.indexOf("_") + 1, key.lastIndexOf("_")), "%" + ((String[])entry.getValue())[0] + "%");
       }
     }
 
     List list = this.service.getAll(tableId, params);
 
     String listTemplate = table.getListTemplate();
 
     Map data = new HashMap();
     data.put("list", list);
 
     String html = this.freemarkEngine.parseByStringTemplate(data, listTemplate);
 
     return getAutoView().addObject("html", html);
   }
 
   @RequestMapping({"detail"})
   @Action(description="查看数据明细")
   public ModelAndView detail(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="page", defaultValue="1") int page, Long tableId, String pkValue)
     throws Exception
   {
     BpmFormTable table = (BpmFormTable)this.bpmFormTableService.getById(tableId);
 
     BpmFormData bpmFormData = this.service.getByKey(tableId, pkValue);
 
     String detailTemplate = table.getDetailTemplate();
 
     Configuration cfg = new Configuration();
     cfg.setClassicCompatible(true);
     StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
     cfg.setTemplateLoader(stringTemplateLoader);
     stringTemplateLoader.putTemplate("detailTemplate", detailTemplate);
 
     Map data = new HashMap();
     data.put("data", bpmFormData.getMainFields());
     data.put("subDatas", bpmFormData.getSubTableMap());
 
     StringWriter writer = new StringWriter();
     cfg.getTemplate("detailTemplate").process(data, writer);
 
     return getAutoView().addObject("html", writer.toString()).addObject("returnUrl", RequestUtil.getPrePage(request));
   }
 }

