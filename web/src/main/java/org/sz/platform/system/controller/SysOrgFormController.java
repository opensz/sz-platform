 package org.sz.platform.system.controller;
 
  import java.util.Date;

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
import org.sz.core.util.ContextUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseFormController;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.system.model.SysOrg;
import org.sz.platform.system.model.SysUser;
import org.sz.platform.system.service.SysOrgService;
import org.sz.platform.system.service.SysUserService;
 
 @Controller
 @RequestMapping({"/platform/system/sysOrg/"})
 public class SysOrgFormController extends BaseFormController
 {
 
   @Resource
   protected SysOrgService sysOrgService;
   
   @Resource
   protected SysUserService sysUserService;
 

   
   @RequestMapping({"save"})
   @Action(description="添加或更新组织架构")
   public void save(HttpServletRequest request, HttpServletResponse response, SysOrg sysOrg, BindingResult bindResult)
     throws Exception
   {
     ResultMessage resultMessage = validForm("sysOrg", sysOrg, bindResult, request);
     if (resultMessage.getResult() == 0) {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     Date now = new Date();
     SysUser curUser = ContextUtil.getCurrentUser();
     String resultMsg = null;
     
     

     
     
     Long parentId = Long.valueOf(RequestUtil.getLong(request, "parentId"));
     if (sysOrg.getOrgId() == null) {
       Long orgId = Long.valueOf(UniqueIdUtil.genId());
       sysOrg.setOrgId(orgId);
       Long supOrgId = Long.valueOf(RequestUtil.getLong(request, "orgSupId"));
       SysOrg supOrg = (SysOrg)this.sysOrgService.getById(supOrgId);

       if (supOrg == null){
    	 sysOrg.setOrgSupId(null);
         sysOrg.setPath(orgId + ".");
       }
       else {
         sysOrg.setPath(supOrg.getPath() + sysOrg.getOrgId() + ".");
       }
       sysOrg.setCreatorId(curUser.getUserId());
       sysOrg.setCreatetime(now);
  
       this.sysOrgService.addOrg(sysOrg);
       
      
       writeResultMessage(response.getWriter(), "{\"orgId\":\"" + orgId + "\",\"action\":\"add\"}", 1);
     } else {
       sysOrg.setUpdateId(curUser.getUserId());
       sysOrg.setUpdatetime(now);
       this.sysOrgService.updOrg(sysOrg);
       
      
       writeResultMessage(response.getWriter(), "{\"orgId\":\"" + sysOrg.getOrgId() + "\",\"action\":\"upd\"}", 1);
     }
   }
 
   @ModelAttribute
   protected SysOrg getFormObject(@RequestParam("orgId") Long orgId, Model model)
     throws Exception
   {
     this.logger.debug("enter SysOrg getFormObject here....");
     SysOrg sysOrg = null;
     if (orgId != null)
       sysOrg = (SysOrg)this.sysOrgService.getById(orgId);
     else {
       sysOrg = new SysOrg();
     }
     return sysOrg;
   }
   

 }

