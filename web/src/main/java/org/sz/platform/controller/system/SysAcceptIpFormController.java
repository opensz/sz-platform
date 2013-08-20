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
import org.sz.platform.model.system.SysAcceptIp;
import org.sz.platform.service.system.SysAcceptIpService;
 
 @Controller
 @RequestMapping({"/platform/system/sysAcceptIp/"})
 public class SysAcceptIpFormController extends BaseFormController
 {
 
   @Resource
   private SysAcceptIpService sysAcceptIpService;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新可访问地址")
   public void save(HttpServletRequest request, HttpServletResponse response, SysAcceptIp sysAcceptIp, BindingResult bindResult)
     throws Exception
   {
     ResultMessage resultMessage = validForm("sysAcceptIp", sysAcceptIp, bindResult, request);
 
     if (resultMessage.getResult() == 0)
     {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     String resultMsg = null;
     if (sysAcceptIp.getAcceptId() == null) {
       sysAcceptIp.setAcceptId(Long.valueOf(UniqueIdUtil.genId()));
       this.sysAcceptIpService.add(sysAcceptIp);
       resultMsg = getText("record.added", new Object[] { "可访问地址" });
     } else {
       this.sysAcceptIpService.update(sysAcceptIp);
       resultMsg = getText("record.updated", new Object[] { "可访问地址" });
     }
     writeResultMessage(response.getWriter(), resultMsg, 1);
   }
 
   @ModelAttribute
   protected SysAcceptIp getFormObject(@RequestParam("acceptId") Long acceptId, Model model)
     throws Exception
   {
     this.logger.debug("enter SysAcceptIp getFormObject here....");
     SysAcceptIp sysAcceptIp = null;
     if (acceptId != null)
       sysAcceptIp = (SysAcceptIp)this.sysAcceptIpService.getById(acceptId);
     else {
       sysAcceptIp = new SysAcceptIp();
     }
     return sysAcceptIp;
   }
 }

