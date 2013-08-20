 package org.sz.platform.controller.system;
 
  import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.sz.core.annotion.Action;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseFormController;
import org.sz.platform.model.system.SubSystem;
import org.sz.platform.model.system.SysUser;
import org.sz.platform.service.system.SubSystemService;
 
 @Controller
 @RequestMapping({"/platform/system/subSystem/"})
 public class SubSystemFormController extends BaseFormController
 {
 
   @Resource
   private SubSystemService subSystemService;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新子系统")
   public void save(HttpServletRequest request, HttpServletResponse response, SubSystem subSystem, BindingResult bindResult)
     throws Exception
   {
     ResultMessage resultMessage = validForm("subSystem", subSystem, bindResult, request);
     if (resultMessage.getResult() == 0) {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     String logo = subSystem.getLogo();
     if (StringUtils.startsWith(logo, request.getContextPath()))
       logo = logo.replaceFirst(request.getContextPath(), "");
     String resultMsg = null;
     try {
       if (subSystem.getSystemId() == 0L)
       {
         int rtn = this.subSystemService.isAliasExist(subSystem.getAlias()).intValue();
         if (rtn > 0) {
           writeResultMessage(response.getWriter(), "输入的系统别名系统中已存在!", 0);
           return;
         }
 
         long systemId = UniqueIdUtil.genId();
         subSystem.setSystemId(systemId);
         SysUser currentUser = ContextUtil.getCurrentUser();
         subSystem.setCreator(currentUser.getUsername());
         subSystem.setCreateBy(currentUser.getUserId());
         subSystem.setCreatetime(new Date());
 
         subSystem.setLogo(logo);
         this.subSystemService.add(subSystem);
         resultMsg = getText("record.added", new Object[] { "子系统管理" });
         writeResultMessage(response.getWriter(), resultMsg, 1);
       }
       else {
         int rtn = this.subSystemService.isAliasExistForUpd(subSystem.getAlias(), Long.valueOf(subSystem.getSystemId())).intValue();
         if (rtn > 0) {
           writeResultMessage(response.getWriter(), "输入的系统别名系统中已存在!", 0);
           return;
         }
         subSystem.setLogo(logo);
         this.subSystemService.update(subSystem);
 
         resultMsg = getText("record.updated", new Object[] { "子系统管理" });
         writeResultMessage(response.getWriter(), resultMsg, 1);
       }
     } catch (Exception ex) {
       writeResultMessage(response.getWriter(), "操作子系统出错:" + ex.getMessage(), 0);
     }
   }
 
   @ModelAttribute
   protected SubSystem getFormObject(@RequestParam("systemId") Long systemId, Model model)
     throws Exception
   {
     this.logger.debug("enter subSystem getFormObject here....");
     SubSystem subSystem = null;
     if (systemId.longValue() > 0L)
       subSystem = (SubSystem)this.subSystemService.getById(systemId);
     else {
       subSystem = new SubSystem();
     }
     return subSystem;
   }
 }

