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
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseFormController;
import org.sz.platform.model.system.SysTypeKey;
import org.sz.platform.service.system.SysTypeKeyService;
 
 @Controller
 @RequestMapping({"/platform/system/sysTypeKey/"})
 public class SysTypeKeyFormController extends BaseFormController
 {
 
   @Resource
   private SysTypeKeyService sysTypeKeyService;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新系统分类键定义")
   public void save(HttpServletRequest request, HttpServletResponse response, SysTypeKey sysTypeKey, BindingResult bindResult)
     throws Exception
   {
     ResultMessage resultMessage = validForm("sysTypeKey", sysTypeKey, bindResult, request);
 
     if (resultMessage.getResult() == 0) {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     String typeKey = sysTypeKey.getTypeKey();
     ResultMessage resultMsg = null;
     if (sysTypeKey.getTypeId().longValue() == 0L) {
       if (this.sysTypeKeyService.isExistKey(typeKey)) {
         String message = "键值已经存在!";
         resultMsg = new ResultMessage(0, message);
       }
       else {
         try {
           sysTypeKey.setTypeId(Long.valueOf(UniqueIdUtil.genId()));
           sysTypeKey.setFlag(Integer.valueOf(0));
           sysTypeKey.setSn(Integer.valueOf(0));
           this.sysTypeKeyService.add(sysTypeKey);
           String message = getText("record.added", new Object[] { "系统分类键定义" });
           resultMsg = new ResultMessage(1, message);
         }
         catch (Exception ex) {
           resultMsg = new ResultMessage(0, "添加系统分类key失败!");
         }
       }
     }
     else if (this.sysTypeKeyService.isKeyExistForUpdate(typeKey, sysTypeKey.getTypeId())) {
       String message = "键值已经存在!";
       resultMsg = new ResultMessage(0, message);
     }
     else
     {
       try {
         this.sysTypeKeyService.update(sysTypeKey);
         String message = getText("record.updated", new Object[] { "系统分类键定义" });
         resultMsg = new ResultMessage(1, message);
       }
       catch (Exception ex) {
         resultMsg = new ResultMessage(0, "修改系统分类key失败!");
       }
     }
 
     writeResultMessage(response.getWriter(), resultMsg);
   }
 
   @ModelAttribute
   protected SysTypeKey getFormObject(@RequestParam("typeId") Long typeId, Model model)
     throws Exception
   {
     this.logger.debug("enter SysTypeKey getFormObject here....");
     SysTypeKey sysTypeKey = null;
     if (typeId.longValue() > 0L)
       sysTypeKey = (SysTypeKey)this.sysTypeKeyService.getById(typeId);
     else {
       sysTypeKey = new SysTypeKey();
     }
     return sysTypeKey;
   }
 }

