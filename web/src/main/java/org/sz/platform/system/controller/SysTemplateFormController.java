 package org.sz.platform.system.controller;
 
  import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.sz.core.annotion.Action;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseFormController;
import org.sz.platform.system.model.SysTemplate;
import org.sz.platform.system.service.SysTemplateService;
 
 @Controller
 @RequestMapping({"/platform/system/sysTemplate/"})
 public class SysTemplateFormController extends BaseFormController
 {
 
   @Resource
   private SysTemplateService sysTemplateService;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新模版管理")
   public void save(HttpServletRequest request, HttpServletResponse response, SysTemplate sysTemplate, BindingResult bindResult)
     throws Exception
   {
     ResultMessage resultMessage = validForm("sysTemplate", sysTemplate, bindResult, request);
 
     if (resultMessage.getResult() == 0)
     {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     String resultMsg = null;
     if (sysTemplate.getTemplateId() == null)
     {
       sysTemplate.setTemplateId(Long.valueOf(UniqueIdUtil.genId()));
       this.sysTemplateService.add(sysTemplate);
       resultMsg = getText("record.added", new Object[] { "模版管理" });
     } else {
       this.sysTemplateService.update(sysTemplate);
       resultMsg = getText("record.updated", new Object[] { "模版管理" });
     }
     writeResultMessage(response.getWriter(), resultMsg, 1);
   }
 
   @ModelAttribute
   protected SysTemplate getFormObject(@RequestParam("templateId") Long templateId, Model model)
     throws Exception
   {
     this.logger.debug("enter SysTemplate getFormObject here....");
     SysTemplate sysTemplate = null;
     if (templateId != null)
       sysTemplate = (SysTemplate)this.sysTemplateService.getById(templateId);
     else {
       sysTemplate = new SysTemplate();
     }
     return sysTemplate;
   }
 }

