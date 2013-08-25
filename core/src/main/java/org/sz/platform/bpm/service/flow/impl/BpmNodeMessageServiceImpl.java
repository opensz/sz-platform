 package org.sz.platform.bpm.service.flow.impl;
 
  import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.bpm.dao.flow.BpmNodeMessageDao;
import org.sz.platform.bpm.model.flow.BpmNodeMessage;
import org.sz.platform.bpm.service.flow.BpmNodeMessageService;
import org.sz.platform.system.dao.MessageDao;
import org.sz.platform.system.model.Message;
 
 @Service("bpmNodeMessageService")
 public class BpmNodeMessageServiceImpl extends BaseServiceImpl<BpmNodeMessage> implements BpmNodeMessageService
 {
 
   @Resource
   private BpmNodeMessageDao dao;
 
   @Resource
   private MessageDao messageDao;
 
   protected IEntityDao<BpmNodeMessage, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public List<BpmNodeMessage> getListByActDefIdNodeId(String actDefId, String nodeId)
   {
     return this.dao.getMessageByActDefIdNodeId(actDefId, nodeId);
   }
 
   public void saveAndEdit(String actDefId, String nodeId, List<Message> messages)
     throws Exception
   {
     this.messageDao.delByActdefidAndNodeid(actDefId, nodeId);
     this.dao.delByActDefId(actDefId);
     BpmNodeMessage bpmMessage = new BpmNodeMessage();
     bpmMessage.setActDefId(actDefId);
     bpmMessage.setNodeId(nodeId);
     for (Message message : messages)
     {
       bpmMessage.setId(Long.valueOf(UniqueIdUtil.genId()));
       bpmMessage.setMessageId(Long.valueOf(UniqueIdUtil.genId()));
       message.setMessageId(bpmMessage.getMessageId());
       this.dao.add(bpmMessage);
       this.messageDao.add(message);
     }
   }
 }

