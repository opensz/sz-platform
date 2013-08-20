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
import org.sz.platform.model.system.Identity;
import org.sz.platform.service.system.IdentityService;
 
 @Controller
 @RequestMapping({"/platform/system/indetity/"})
 public class IndetityFormController extends BaseFormController
 {
 
   @Resource
   private IdentityService identityService;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新流水号生成")
   public void save(HttpServletRequest request, HttpServletResponse response, Identity identity, BindingResult bindResult)
     throws Exception
   {
     ResultMessage resultMessage = validForm("indetity", identity, bindResult, request);
 
     if (resultMessage.getResult() == 0)
     {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     String resultMsg = null;
     if (identity.getId().longValue() == 0L) {
       boolean rtn = this.identityService.isAliasExisted(identity.getAlias());
       if (rtn) {
         writeResultMessage(response.getWriter(), "别名已经存在!", 0);
         return;
       }
 
       identity.setId(Long.valueOf(UniqueIdUtil.genId()));
       identity.setCurDate(identityService.getCurDate());
       this.identityService.add(identity);
       resultMsg = getText("record.added", new Object[] { "流水号生成" });
     }
     else {
       boolean rtn = this.identityService.isAliasExistedByUpdate(identity);
       if (rtn) {
         writeResultMessage(response.getWriter(), "别名已经存在!", 0);
         return;
       }
       this.identityService.update(identity);
       resultMsg = getText("record.updated", new Object[] { "流水号生成" });
     }
     writeResultMessage(response.getWriter(), resultMsg, 1);
   }
 
   @ModelAttribute
   protected Identity getFormObject(@RequestParam("id") Long id, Model model)
     throws Exception
   {
     this.logger.debug("enter Indetity getFormObject here....");
     Identity indetity = null;
     if (id.longValue() > 0L)
       indetity = (Identity)this.identityService.getById(id);
     else {
       indetity = new Identity();
     }
     return indetity;
   }
 }

