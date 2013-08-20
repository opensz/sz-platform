 package org.sz.platform.controller.system;
 
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
import org.sz.core.encrypt.EncryptUtil;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.PinyinUtil;
import org.sz.core.web.controller.BaseFormController;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.model.system.SysUser;
import org.sz.platform.service.system.SysUserOrgService;
import org.sz.platform.service.system.SysUserService;
 
 @Controller
 @RequestMapping({"/platform/system/sysUser/"})
 public class SysUserFormController extends BaseFormController
 {
   public static Boolean SYN_USER=true;
   
   @Resource
   protected SysUserService sysUserService;
   
   @Resource
   private SysUserOrgService sysUserOrgService;
   
   @RequestMapping({"save"})
   @Action(description="添加或更新用户表")
   public void save(HttpServletRequest request, HttpServletResponse response, SysUser sysUser, BindingResult bindResult)
     throws Exception
   {
     /*ResultMessage resultMessage = validForm("sysUser", sysUser, bindResult, request);
 
     if (resultMessage.getResult() == 0) {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }*/
     String resultMsg = null;
 
     Long[] aryOrgId = RequestUtil.getLongAry(request, "orgId");
     Long[] aryIsDept = RequestUtil.getLongAry(request, "isDept");
     Long orgIdPrimary = Long.valueOf(RequestUtil.getLong(request, "orgIdPrimary"));
     Long deptIdPrimary = Long.valueOf(RequestUtil.getLong(request, "deptIdPrimary"));
     Long[] aryChargeOrg = RequestUtil.getLongAry(request, "chargeOrgId");
     Long[] aryPosId = RequestUtil.getLongAry(request, "posId");
     Long posIdPrimary = Long.valueOf(RequestUtil.getLong(request, "posIdPrimary"));
     Long[] aryRoleId = RequestUtil.getLongAry(request, "roleId");
     Integer bySelf = Integer.valueOf(RequestUtil.getInt(request, "bySelf"));
 
     if ((BeanUtils.isNotEmpty(aryOrgId)) && (orgIdPrimary.longValue() == 0L)) {
       resultMsg = getText("record.add.fail.user", new Object[] { "请选择主组织！" });
       writeResultMessage(response.getWriter(), resultMsg, 0);
       return;
     }
     if ((BeanUtils.isNotEmpty(aryPosId)) && (posIdPrimary.longValue() == 0L)) {
       resultMsg = getText("record.add.fail.user", new Object[] { "请选择主岗位！" });
       writeResultMessage(response.getWriter(), resultMsg, 0);
       return;
     }
     String spell=PinyinUtil.getFirstSpell(sysUser.getFullname());

     sysUser.setFirstSpell(spell);

     if (sysUser.getUserId() == null)
     {
       boolean isExist = this.sysUserService.isAccountExist(sysUser.getAccount());
       if (isExist) {
         resultMsg = getText("record.add.fail", new Object[] { "该账号已经存在！" });
         writeResultMessage(response.getWriter(), resultMsg, 0);
         return;
       }
 
       String enPassword = EncryptUtil.encryptSha256(sysUser.getPassword());
       sysUser.setPassword(enPassword);
      
       Long userId=this.sysUserService.saveUser(bySelf, sysUser, aryOrgId, aryChargeOrg,aryIsDept, orgIdPrimary,deptIdPrimary, aryPosId, posIdPrimary, aryRoleId);
       
    
       resultMsg = getText("record.added", new Object[] { "用户表" });
       writeResultMessage(response.getWriter(), resultMsg, 1);
     }
     else
     {
       boolean isExist = this.sysUserService.isAccountExistForUpd(sysUser.getUserId(), sysUser.getAccount());
       if (isExist) {
         resultMsg = getText("record.add.fail", new Object[] { "该账号已经存在！" });
         writeResultMessage(response.getWriter(), resultMsg, 0);
         return;
       }
     
       this.sysUserService.saveUser(bySelf, sysUser, aryOrgId, aryChargeOrg,aryIsDept, orgIdPrimary,deptIdPrimary, aryPosId, posIdPrimary, aryRoleId);
      
       resultMsg = getText("record.updated", new Object[] { "用户表" });
 
       writeResultMessage(response.getWriter(), resultMsg, 1);
     }
   }
 
   @ModelAttribute
   protected SysUser getFormObject(@RequestParam("userId") Long userId, Model model)
     throws Exception
   {
     this.logger.debug("enter SysUser getFormObject here....");
     SysUser sysUser = null;
     if (userId != null) {
       sysUser = (SysUser)this.sysUserService.getById(userId);
     }
     else {
       sysUser = new SysUser();
     }
     return sysUser;
   }
   
 }

