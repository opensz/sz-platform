 package org.sz.platform.system.dao.impl;
 
  import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.MessageReplyDao;
import org.sz.platform.system.model.MessageReply;
 
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

