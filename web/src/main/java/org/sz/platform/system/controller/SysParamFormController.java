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
import org.sz.platform.system.model.SysParam;
import org.sz.platform.system.service.SysParamService;
 
 @Controller
 @RequestMapping({"/platform/system/sysParam/"})
 public class SysParamFormController extends BaseFormController
 {
 
   @Resource
   private SysParamService sysParamService;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新系统参数属性")
   public void save(HttpServletRequest request, HttpServletResponse response, SysParam sysParam, BindingResult bindResult)
     throws Exception
   {
     ResultMessage resultMessage = validForm("sysParam", sysParam, bindResult, request);
 
     if (resultMessage.getResult() == 0)
     {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     String resultMsg = null;
     if (sysParam.getParamId() == null) {
       sysParam.setParamId(Long.valueOf(UniqueIdUtil.genId()));
       this.sysParamService.add(sysParam);
       resultMsg = getText("record.added", new Object[] { "系统参数属性" });
     } else {
       this.sysParamService.update(sysParam);
       resultMsg = getText("record.updated", new Object[] { "系统参数属性" });
     }
     writeResultMessage(response.getWriter(), resultMsg, 1);
   }
 
   @ModelAttribute
   protected SysParam getFormObject(@RequestParam("paramId") Long paramId, Model model)
     throws Exception
   {
     this.logger.debug("enter SysParam getFormObject here....");
     SysParam sysParam = null;
     if (paramId != null)
       sysParam = (SysParam)this.sysParamService.getById(paramId);
     else {
       sysParam = new SysParam();
     }
     return sysParam;
   }
 }

