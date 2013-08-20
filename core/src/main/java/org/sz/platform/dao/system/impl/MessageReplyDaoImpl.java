 package org.sz.platform.dao.system.impl;
 
  import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.dao.system.MessageReplyDao;
import org.sz.platform.model.system.MessageReply;
 
 @Repository("messageReplyDao")
 public class MessageReplyDaoImpl extends BaseDaoImpl<MessageReply> implements MessageReplyDao
 {
   public Class getEntityClass()
   {
     return MessageReply.class;
   }
 
   public List<MessageReply> getReplyByMsgId(Long messageId)
   {
     return getBySqlKey("getReplyByMsgId", messageId);
   }
 }

