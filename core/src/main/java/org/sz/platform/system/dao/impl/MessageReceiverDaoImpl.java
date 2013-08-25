 package org.sz.platform.system.dao.impl;
 
  import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.MessageReceiverDao;
import org.sz.platform.system.model.MessageReceiver;
 
 @Repository("messageReceiverDao")
 public class MessageReceiverDaoImpl extends BaseDaoImpl<MessageReceiver> implements MessageReceiverDao
 {
   public Class getEntityClass()
   {
     return MessageReceiver.class;
   }
 
   public List<MessageReceiver> getMessageReceiverList(Long messageId)
   {
     Map param = new HashMap();
     param.put("messageId", messageId);
     return getBySqlKey("getAll", param);
   }
 
   public List<Map> getReadReplyByUser(Long messageId)
   {
     Map param = new HashMap();
     param.put("messageId", messageId);
     String statement = getIbatisMapperNamespace() + "." + "getReadReplyByUser";
     List list = getSqlSessionTemplate().selectList(statement, param);
     return list;
   }
 
   public List<Map> getReadReplyByPath(Long messageId, String path)
   {
     Map param = new HashMap();
     param.put("messageId", messageId);
     param.put("path", path);
     String statement = getIbatisMapperNamespace() + "." + "getReadReplyByPath";
     List list = getSqlSessionTemplate().selectList(statement, param);
     return list;
   }
 }

