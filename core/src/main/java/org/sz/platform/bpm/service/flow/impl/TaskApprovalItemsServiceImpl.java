 package org.sz.platform.bpm.service.flow.impl;
 
  import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.bpm.dao.flow.TaskApprovalItemsDao;
import org.sz.platform.bpm.model.flow.TaskApprovalItems;
import org.sz.platform.bpm.service.flow.TaskApprovalItemsService;
 
 @Service("taskApprovalItemsService")
 public class TaskApprovalItemsServiceImpl extends BaseServiceImpl<TaskApprovalItems> implements TaskApprovalItemsService
 {
 
   @Resource
   private TaskApprovalItemsDao dao;
 
   protected IEntityDao<TaskApprovalItems, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public TaskApprovalItems getFlowApproval(String actDefId, int isGlobal)
   {
     return this.dao.getFlowApproval(actDefId, isGlobal);
   }
 
   public TaskApprovalItems getTaskApproval(String actDefId, String nodeId, int isGlobal)
   {
     return this.dao.getTaskApproval(actDefId, nodeId, isGlobal);
   }
 
   public void delFlowApproval(String actDefId, int isGlobal)
   {
     this.dao.delFlowApproval(actDefId, isGlobal);
   }
 
   public void delTaskApproval(String actDefId, String nodeId, int isGlobal)
   {
     this.dao.delTaskApproval(actDefId, nodeId, isGlobal);
   }
 
   public void addTaskApproval(String exp, String isGlobal, String actDefId, Long setId, String nodeId)
     throws Exception
   {
     TaskApprovalItems taItem = null;
     taItem = new TaskApprovalItems();
     taItem.setItemId(Long.valueOf(UniqueIdUtil.genId()));
     taItem.setActDefId(actDefId);
     if (!isGlobal.equals("1"))
     {
       taItem.setSetId(setId);
       taItem.setNodeId(nodeId);
       taItem.setIsGlobal(TaskApprovalItems.notGlobal);
     } else {
       taItem.setIsGlobal(TaskApprovalItems.global);
     }
     taItem.setExpItems(exp);
     add(taItem);
   }
 
   public List<String> getApprovalByActDefId(String actDefId, String nodeId)
   {
     List taskAppItemsList = new ArrayList();
     List<TaskApprovalItems> taskAppItems = this.dao.getApprovalByActDefId(actDefId, nodeId);
     if (BeanUtils.isNotEmpty(taskAppItems)) {
       for (TaskApprovalItems taskAppItem : taskAppItems) {
         String[] itemArr = taskAppItem.getExpItems().split("\r\n");
         for (String item : itemArr) {
           taskAppItemsList.add(item);
         }
       }
     }
 
     return taskAppItemsList;
   }
 }

