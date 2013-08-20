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
import org.sz.platform.model.system.MessageReceiver;
import org.sz.platform.service.system.MessageReceiverService;
 
 @Controller
 @RequestMapping({"/platform/system/messageReceiver/"})
 public class MessageReceiverFormController extends BaseFormController
 {
 
   @Resource
   private MessageReceiverService messageReceiverService;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新消息接收者")
   public void save(HttpServletRequest request, HttpServletResponse response, MessageReceiver messageReceiver, BindingResult bindResult)
     throws Exception
   {
     ResultMessage resultMessage = validForm("messageReceiver", messageReceiver, bindResult, request);
 
     if (resultMessage.getResult() == 0) {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     String resultMsg = null;
     if (messageReceiver.getId() == null) {
       messageReceiver.setId(Long.valueOf(UniqueIdUtil.genId()));
       this.messageReceiverService.add(messageReceiver);
       resultMsg = getText("record.added", new Object[] { "消息接收者" });
     } else {
       this.messageReceiverService.update(messageReceiver);
       resultMsg = getText("record.updated", new Object[] { "消息接收者" });
     }
     writeResultMessage(response.getWriter(), resultMsg, 1);
   }
 
   @ModelAttribute
   protected MessageReceiver getFormObject(@RequestParam("id") Long id, Model model)
     throws Exception
   {
     this.logger.debug("enter MessageReceiver getFormObject here....");
     MessageReceiver messageReceiver = null;
     if (id != null)
       messageReceiver = (MessageReceiver)this.messageReceiverService.getById(id);
     else {
       messageReceiver = new MessageReceiver();
     }
     return messageReceiver;
   }
 }

