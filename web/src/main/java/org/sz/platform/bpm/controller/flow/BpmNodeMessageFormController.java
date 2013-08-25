 package org.sz.platform.bpm.controller.flow;
 
  import java.util.ArrayList;
import java.util.List;

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
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseFormController;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.flow.BpmNodeMessage;
import org.sz.platform.bpm.service.flow.BpmNodeMessageService;
import org.sz.platform.system.model.Message;
import org.sz.platform.system.model.SysUser;
 
 @Controller
 @RequestMapping({"/platform/bpm/bpmNodeMessage/"})
 public class BpmNodeMessageFormController extends BaseFormController
 {
 
   @Resource
   private BpmNodeMessageService bpmNodeMessageService;
 
   @RequestMapping({"save"})
   @Action(description="添加或更新流程节点邮件")
   public void save(HttpServletRequest request, HttpServletResponse response, BpmNodeMessage bpmNodeMessage, BindingResult bindResult)
     throws Exception
   {
     ResultMessage resultMessage = validForm("bpmNodeMessage", bpmNodeMessage, bindResult, request);
     if (resultMessage.getResult() == 0)
     {
       writeResultMessage(response.getWriter(), resultMessage);
       return;
     }
     SysUser curUser = ContextUtil.getCurrentUser();
     String subject_mail = RequestUtil.getString(request, "subject_mail");
     String receiver_mail = RequestUtil.getString(request, "receiver_mail");
     String actDefId = RequestUtil.getString(request, "actDefId");
     String nodeId = RequestUtil.getString(request, "nodeId");
     String copyTo_mail = RequestUtil.getString(request, "copyTo_mail");
     String bcc_mail = RequestUtil.getString(request, "bcc_mail");
     Long templateId_mail = Long.valueOf(RequestUtil.getLong(request, "templateId_mail"));
 
     String receiver_mobile = RequestUtil.getString(request, "receiver_mobile");
     Long templateId_mobile = Long.valueOf(RequestUtil.getLong(request, "templateId_mobile"));
 
     String subject_inner = RequestUtil.getString(request, "subject_inner");
     String receiver_inner = RequestUtil.getString(request, "receiver_inner");
     Long templateId_inner = Long.valueOf(RequestUtil.getLong(request, "templateId_inner"));
 
     List messages = new ArrayList();
     if (!receiver_mail.isEmpty())
     {
       Message message = new Message();
       message.setSubject(subject_mail);
       message.setReceiver(receiver_mail);
       message.setCopyTo(copyTo_mail);
       message.setBcc(bcc_mail);
       message.setTemplateId(templateId_mail);
       message.setMessageType(Message.MAIL_TYPE);
       message.setFromUser(curUser.getEmail());
       messages.add(message);
     }
     if (!receiver_mobile.isEmpty())
     {
       Message message = new Message();
       message.setSubject("");
       message.setReceiver(receiver_mobile);
       message.setCopyTo("");
       message.setBcc("");
       message.setTemplateId(templateId_mobile);
       message.setMessageType(Message.MOBILE_TYPE);
       message.setFromUser(curUser.getFullname());
       message.setActDefId(actDefId);
       message.setNodeId(nodeId);
       messages.add(message);
     }
     if (!receiver_inner.isEmpty())
     {
       Message message = new Message();
       message.setSubject(subject_inner);
       message.setReceiver(receiver_inner);
       message.setCopyTo("");
       message.setBcc("");
       message.setTemplateId(templateId_inner);
       message.setMessageType(Message.INNER_TYPE);
       message.setFromUser(curUser.getFullname());
       message.setActDefId(actDefId);
       message.setNodeId(nodeId);
       messages.add(message);
     }
     this.bpmNodeMessageService.saveAndEdit(actDefId, nodeId, messages);
     writeResultMessage(response.getWriter(), "流程节点消息设置成功!", 1);
   }
 
   @ModelAttribute
   protected BpmNodeMessage getFormObject(@RequestParam("id") Long id, Model model)
     throws Exception
   {
     this.logger.debug("enter BpmNodeMessage getFormObject here....");
     BpmNodeMessage bpmNodeMessage = null;
     if (id != null)
       bpmNodeMessage = (BpmNodeMessage)this.bpmNodeMessageService.getById(id);
     else {
       bpmNodeMessage = new BpmNodeMessage();
     }
     return bpmNodeMessage;
   }
 }

