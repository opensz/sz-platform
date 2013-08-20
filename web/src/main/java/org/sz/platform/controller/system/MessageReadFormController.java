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
import org.sz.platform.model.system.MessageRead;
import org.sz.platform.service.system.MessageReadService;
 
 @Controller
 @RequestMapping({"/platform/system/messageRead/"})
 public class MessageReadFormController extends BaseFormController
 {
 
   @Resource
   private MessageReadService messageReadService;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新接收状态")
   public void save(HttpServletRequest request, HttpServletResponse response, MessageRead messageRead, BindingResult bindResult)
     throws Exception
   {
     ResultMessage resultMessage = validForm("messageRead", messageRead, bindResult, request);
 
     if (resultMessage.getResult() == 0)
     {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     String resultMsg = null;
     if (messageRead.getId() == null) {
       messageRead.setId(Long.valueOf(UniqueIdUtil.genId()));
       this.messageReadService.add(messageRead);
       resultMsg = getText("record.added", new Object[] { "接收状态" });
     } else {
       this.messageReadService.update(messageRead);
       resultMsg = getText("record.updated", new Object[] { "接收状态" });
     }
     writeResultMessage(response.getWriter(), resultMsg, 1);
   }
 
   @ModelAttribute
   protected MessageRead getFormObject(@RequestParam("id") Long id, Model model)
     throws Exception
   {
     this.logger.debug("enter MessageRead getFormObject here....");
     MessageRead messageRead = null;
     if (id != null)
       messageRead = (MessageRead)this.messageReadService.getById(id);
     else {
       messageRead = new MessageRead();
     }
     return messageRead;
   }
 }

