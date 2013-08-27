 package org.sz.platform.bpm.service.flow;
 
  import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.sz.core.jms.MessageProducer;
import org.sz.core.model.InnerMessage;
import org.sz.core.model.MailModel;
import org.sz.core.model.SmsMobile;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.StringUtil;
import org.sz.platform.system.model.Message;
import org.sz.platform.system.service.MessageService;
 
 public class MessageTask
   implements JavaDelegate
 {
   public void execute(DelegateExecution execution)
     throws Exception
   {
     ExecutionEntity ent = (ExecutionEntity)execution;
 
     String nodeId = ent.getActivityId();
     String actDefId = ent.getProcessDefinitionId();
 
     List list = getSendList(actDefId, nodeId);
 
     MessageProducer messageProducer = (MessageProducer)ContextUtil.getBean("messageProducer");
     for (int i = 0; i < list.size(); i++)
       messageProducer.send(list.get(i));
   }
 
   private List getSendList(String actDefId, String nodeId)
   {
     List list = new ArrayList();
     MessageService MessageService = (MessageService)ContextUtil.getBean("messageService");
 
     Map dataMap = MessageService.getMapByActDefIdNodeId(actDefId, nodeId);
     Message mailMessage = (Message)dataMap.get(Message.MAIL_TYPE);
     if (mailMessage != null) {
       MailModel model = getMailModel(mailMessage);
       list.add(model);
     }
 
     Message mobileMessage = (Message)dataMap.get(Message.MOBILE_TYPE);
     if (mobileMessage != null) {
       SmsMobile model = getMobileModel(mobileMessage);
       list.add(model);
     }
     Message innerMessage = (Message)dataMap.get(Message.INNER_TYPE);
     if (innerMessage != null) {
       InnerMessage innerModel = getInnerModel(innerMessage);
       list.add(innerModel);
     }
     return list;
   }
 
   public MailModel getMailModel(Message messageModel)
   {
     String[] to = new String[0];
     String toEmail = "";
     String toName = "";
     String[] cc = new String[0];
     String ccEmail = "";
     String[] bcc = new String[0];
     String bccEmail = "";
     String[] receive = new String[0];
     Date sendDate = new Date();
     if (StringUtil.isNotEmpty(messageModel.getReceiver())) {
       Map<String, String> mailMap = splitString(messageModel.getReceiver());
       for (Map.Entry entry : mailMap.entrySet()) {
         toName = (String)entry.getKey();
         toEmail = (String)entry.getValue();
       }
       if (StringUtil.isNotEmpty(toEmail))
         to = toEmail.split(",");
     }
     if (StringUtil.isNotEmpty(messageModel.getCopyTo())) {
       Map<String, String> ccMap = splitString(messageModel.getCopyTo());
       for (Map.Entry entry : ccMap.entrySet()) {
         ccEmail = (String)entry.getValue();
       }
       if (StringUtil.isNotEmpty(ccEmail))
         cc = ccEmail.split(",");
     }
     if (StringUtil.isNotEmpty(messageModel.getBcc())) {
       Map<String, String> bccMap = splitString(messageModel.getBcc());
       for (Map.Entry entry : bccMap.entrySet()) {
         bccEmail = (String)entry.getValue();
       }
       if (StringUtil.isNotEmpty(bccEmail))
         bcc = bccEmail.split(",");
     }
     MailModel mailModel = new MailModel();
     mailModel.setSubject(messageModel.getSubject());
     mailModel.setContent(messageModel.getSysTemplate().getContent().replace("${收件人}", toName).replace("${发件人}", messageModel.getFromUser()));
     mailModel.setTo(to);
     mailModel.setCc(cc);
     mailModel.setBcc(bcc);
     mailModel.setFrom(messageModel.getFromUser());
     mailModel.setSendDate(sendDate);
 
     return mailModel;
   }
 
   public SmsMobile getMobileModel(Message messageModel)
   {
     String toName = "";
     String to = "";
     if (StringUtil.isNotEmpty(messageModel.getReceiver())) {
       Map<String, String> map = splitString(messageModel.getBcc());
       for (Map.Entry entry : map.entrySet()) {
         toName = (String)entry.getKey();
         to = (String)entry.getValue();
       }
     }
     Date sendDate = new Date();
     SmsMobile smsMobile = new SmsMobile();
     smsMobile.setPhoneNumber(to);
     smsMobile.setSmsContent(messageModel.getSysTemplate().getContent());
     smsMobile.setUserName(messageModel.getFromUser());
     smsMobile.setSendTime(sendDate);
     return smsMobile;
   }
 
   public InnerMessage getInnerModel(Message messageModel)
   {
     String toName = "";
     String to = "";
     if (StringUtil.isNotEmpty(messageModel.getReceiver())) {
       Map<String, String> map = splitString(messageModel.getBcc());
       for (Map.Entry entry : map.entrySet()) {
         toName = (String)entry.getKey();
         to = (String)entry.getValue();
       }
     }
     Date sendDate = new Date();
     InnerMessage innerMessage = new InnerMessage();
     innerMessage.setSubject(messageModel.getSubject());
     innerMessage.setContent(messageModel.getSysTemplate().getContent());
     innerMessage.setTo(to);
     innerMessage.setToName(toName);
     innerMessage.setFrom("0");
     innerMessage.setSendDate(sendDate);
     return innerMessage;
   }
 
   private Map<String, String> splitString(String message) {
     Map reMap = new HashMap();
     if (message == null) return reMap;
     String[] strs = message.split(",");
     Pattern pattern = Pattern.compile("(.*)\\((.*)\\)");
     String keys = "";
     String values = "";
     for (String str : strs) {
       Matcher match = pattern.matcher(str);
       if (match.find()) {
         if (keys == "")
           keys = keys + match.group(1);
         else
           keys = keys + "," + match.group(1);
         if (values == "")
           values = values + match.group(2);
         else
           values = values + "," + match.group(2);
       }
     }
     reMap.put(keys, values);
     return reMap;
   }
 }

