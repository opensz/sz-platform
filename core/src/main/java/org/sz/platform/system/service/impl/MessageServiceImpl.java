 package org.sz.platform.system.service.impl;
 
  import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.system.dao.MessageDao;
import org.sz.platform.system.model.Message;
import org.sz.platform.system.service.MessageService;
 
 @Service("messageService")
 public class MessageServiceImpl extends BaseServiceImpl<Message> implements MessageService
 {
 
   @Resource
   private MessageDao dao;
 
   protected IEntityDao<Message, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public List<Message> getListByActDefIdNodeId(String actDefId, String nodeId)
   {
     return this.dao.getListByActDefIdNodeId(actDefId, nodeId);
   }
 
   public Map<Integer, Message> getMapByActDefIdNodeId(String actDefId, String nodeId)
   {
     List<Message> instList = getListByActDefIdNodeId(actDefId, nodeId);
     Map dataMap = new HashMap();
     if ((instList != null) && (instList.size() > 0))
     {
       for (Message mesModel : instList)
       {
         Integer messageType = mesModel.getMessageType();
         dataMap.put(messageType, mesModel);
       }
     }
     return dataMap;
   }
 }

