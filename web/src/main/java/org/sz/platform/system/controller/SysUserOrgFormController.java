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
import org.sz.platform.system.model.SysUserOrg;
import org.sz.platform.system.service.SysUserOrgService;
 
 @Controller
 @RequestMapping({"/platform/system/sysUserOrg/"})
 public class SysUserOrgFormController extends BaseFormController
 {
 
   @Resource
   private SysUserOrgService sysUserOrgService;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新用户所属组织或部门")
   public void save(HttpServletRequest request, HttpServletResponse response, SysUserOrg sysUserOrg, BindingResult bindResult)
     throws Exception
   {
     ResultMessage resultMessage = validForm("sysUserOrg", sysUserOrg, bindResult, request);
 
     if (resultMessage.getResult() == 0)
     {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     String resultMsg = null;
     if (sysUserOrg.getUserOrgId() == null) {
       sysUserOrg.setUserOrgId(Long.valueOf(UniqueIdUtil.genId()));
       this.sysUserOrgService.add(sysUserOrg);
       resultMsg = getText("record.added", new Object[] { "用户所属组织或部门" });
     } else {
       this.sysUserOrgService.update(sysUserOrg);
       resultMsg = getText("record.updated", new Object[] { "用户所属组织或部门" });
     }
     writeResultMessage(response.getWriter(), resultMsg, 1);
   }
 
   @ModelAttribute
   protected SysUserOrg getFormObject(@RequestParam("userOrgId") Long userOrgId, Model model)
     throws Exception
   {
     this.logger.debug("enter SysUserOrg getFormObject here....");
     SysUserOrg sysUserOrg = null;
     if (userOrgId != null)
       sysUserOrg = (SysUserOrg)this.sysUserOrgService.getById(userOrgId);
     else {
       sysUserOrg = new SysUserOrg();
     }
     return sysUserOrg;
   }
 }

