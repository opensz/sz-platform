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
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.system.dao.SubSystemDao;
import org.sz.platform.system.model.SubSystem;
import org.sz.platform.system.model.SysRole;
import org.sz.platform.system.service.SysRoleService;
 
 @Controller
 @RequestMapping({"/platform/system/sysRole/"})
 public class SysRoleFormController extends BaseFormController
 {
 
   @Resource
   private SysRoleService sysRoleService;
 
   @Resource
   private SubSystemDao subSystemDao;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新系统角色表")
   public void save(HttpServletRequest request, HttpServletResponse response, SysRole sysRole, BindingResult bindResult)
     throws Exception
   {
     Long systemId = Long.valueOf(RequestUtil.getLong(request, "systemId"));
     String systemAlias = "";
     if (systemId.longValue() > 0L) {
       SubSystem subSystem = (SubSystem)this.subSystemDao.getById(systemId);
       systemAlias = subSystem.getAlias() + "_";
     }
     ResultMessage resultMessage = validForm("sysRole", sysRole, bindResult, request);
     if (resultMessage.getResult() == 0) {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     String resultMsg = null;
     if (sysRole.getRoleId() == null) {
       String tmpAlias = sysRole.getAlias();
       String alias = systemAlias + tmpAlias;
 
       boolean isExist = this.sysRoleService.isExistRoleAlias(alias);
       if (!isExist) {
         sysRole.setRoleId(Long.valueOf(UniqueIdUtil.genId()));
         sysRole.setAlias(alias);
         this.sysRoleService.add(sysRole);
         resultMsg = getText("record.added", new Object[] { "系统角色" });
         writeResultMessage(response.getWriter(), resultMsg, 1);
       } else {
         resultMsg = getText("角色别名：[" + tmpAlias + "]已存在");
         writeResultMessage(response.getWriter(), resultMsg, 0);
       }
     } else {
       String tmpAlias = sysRole.getAlias();
       String alias = systemAlias + tmpAlias;
       Long roleId = sysRole.getRoleId();
       boolean isExist = this.sysRoleService.isExistRoleAliasForUpd(alias, roleId);
       if (isExist) {
         resultMsg = getText("角色别名：[" + tmpAlias + "]已存在");
         writeResultMessage(response.getWriter(), resultMsg, 0);
       }
       else {
         sysRole.setAlias(alias);
         this.sysRoleService.update(sysRole);
         resultMsg = getText("record.updated", new Object[] { "系统角色" });
         writeResultMessage(response.getWriter(), resultMsg, 1);
       }
     }
   }
 
   @ModelAttribute
   protected SysRole getFormObject(@RequestParam("roleId") Long roleId, Model model)
     throws Exception
   {
     this.logger.debug("enter SysRole getFormObject here....");
     SysRole sysRole = null;
     if (roleId != null)
       sysRole = (SysRole)this.sysRoleService.getById(roleId);
     else {
       sysRole = new SysRole();
     }
     return sysRole;
   }
 }

