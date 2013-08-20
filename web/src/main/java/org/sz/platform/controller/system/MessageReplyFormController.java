 package org.sz.platform.controller.system;
 
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
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseFormController;
import org.sz.platform.model.system.MessageReply;
import org.sz.platform.service.system.MessageReplyService;
 
 @Controller
 @RequestMapping({"/platform/system/messageReply/"})
 public class MessageReplyFormController extends BaseFormController
 {
 
   @Resource
   private MessageReplyService messageReplyService;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新消息回复")
   public void save(HttpServletRequest request, HttpServletResponse response, MessageReply messageReply, BindingResult bindResult)
     throws Exception
   {
     ResultMessage resultMessage = validForm("messageReply", messageReply, bindResult, request);
 
     if (resultMessage.getResult() == 0)
     {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     String resultMsg = null;
     Date now = new Date();
     if (messageReply.getId() == null) {
       messageReply.setId(Long.valueOf(UniqueIdUtil.genId()));
       messageReply.setReplyTime(now);
       this.messageReplyService.add(messageReply);
       resultMsg = getText("record.added", new Object[] { "消息回复" });
     } else {
       this.messageReplyService.update(messageReply);
       resultMsg = getText("record.updated", new Object[] { "消息回复" });
     }
     writeResultMessage(response.getWriter(), resultMsg, 1);
   }
 
   @ModelAttribute
   protected MessageReply getFormObject(@RequestParam("id") Long id, Model model)
     throws Exception
   {
     this.logger.debug("enter MessageReply getFormObject here....");
     MessageReply messageReply = null;
     if (id != null)
       messageReply = (MessageReply)this.messageReplyService.getById(id);
     else {
       messageReply = new MessageReply();
     }
     return messageReply;
   }
 }

