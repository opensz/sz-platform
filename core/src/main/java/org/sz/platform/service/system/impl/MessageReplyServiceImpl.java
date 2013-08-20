 package org.sz.platform.service.system.impl;
 
  import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.dao.system.MessageReplyDao;
import org.sz.platform.model.system.MessageReply;
import org.sz.platform.model.system.SysUser;
import org.sz.platform.service.system.MessageReplyService;
 
 @Service("messageReplyService")
 public class MessageReplyServiceImpl extends BaseServiceImpl<MessageReply> implements MessageReplyService
 {
 
   @Resource
   private MessageReplyDao dao;
 
   protected IEntityDao<MessageReply, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public void saveReply(MessageReply messageReply, SysUser sysUser)
     throws Exception
   {
     messageReply.setId(Long.valueOf(UniqueIdUtil.genId()));
     messageReply.setReplyId(sysUser.getUserId());
     messageReply.setReply(sysUser.getFullname());
     Date now = new Date();
     messageReply.setReplyTime(now);
     add(messageReply);
   }
 
   public List<MessageReply> getReplyByMsgId(Long messageId)
   {
     return this.dao.getReplyByMsgId(messageId);
   }
 }

